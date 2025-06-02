
# Determine Level
````java

public User registerUser(User user) {
    userRepository.save(user);
     
     // Determine the new user's level after registration and hierarchy update 
     user.setLevel(determineLevel(user.getId(), user.getReserveBalance())); 
     userRepository.save(user);
}
````

````java
public int determineLevel(Long userId, double reserveBalance) {
	int levelACount = calculateLevelACount(userId);
	int levelBCount = calculateLevelBCount(userId);
	int levelCCount = calculateLevelCCount(userId);
	int levelBPlusCCount = levelBCount + levelCCount;

	if (reserveBalance >= 50 && reserveBalance <= 100) {
		return 1;
	}
	if (reserveBalance >= 500 && reserveBalance <= 2000 && levelACount >= 4 && levelBPlusCCount >= 5) {
		return 2;
	}
	if (reserveBalance >= 2000 && reserveBalance <= 5000 && levelACount >= 6 && levelBPlusCCount >= 20) {
		return 3;
	}
	if (reserveBalance >= 5000 && reserveBalance <= 10000 && levelACount >= 15 && levelBPlusCCount >= 35) {
		return 4;
	}
	return 0; // Not eligible
}
````

#### calculateLevelACount: 
````java
public int calculateLevelACount(Long userId) {
   return userHierarchyRepository.findByAncestor(userId).stream()
		   .filter(path -> path.getDepth() == 1)
		   .mapToInt(path -> 1)
		   .sum();
}
````

#### calculateLevelBCount:
````java
public int calculateLevelBCount(Long userId) {
	List<Long> levelAIds = userHierarchyRepository.findByAncestorAndDepth(userId, 1)
			.stream()
			.map(UserHierarchy::getDescendant)
			.toList();
	return userHierarchyRepository.findByAncestorsAndDepth(levelAIds, 2).size();
}
````

#### calculateLevelCCount:
````java
public int calculateLevelCCount(Long userId) {
	List<Long> levelAIds = userHierarchyRepository.findByAncestorAndDepth(userId, 1)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        List<Long> levelBIds = userHierarchyRepository.findByAncestorsAndDepth(levelAIds, 2)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();
        return userHierarchyRepository.findByAncestorsAndDepth(levelBIds, 3).size();
}
````

## Can we use some **Design Pattern** to Decoupling this logic
For the design of `determineLevel`, where the level calculation logic grows in complexity and each level's count might depend on others (with potential future expansion), you should aim for a **scalable**, **testable**, **and maintainable** architecture. Here are two design patterns that would suit your scenario well:

## ✅ Best Fit: Chain of Responsibility Pattern
Each level determination is handled by a separate handler class. Each handler checks if the user meets its level's criteria and then either returns the level or passes to the next handler.

### Sample Implementation:
````java
public interface LevelHandler {
    Integer determineLevel(User user, LevelContext context);
    void setNext(LevelHandler next);
}

public abstract class AbstractLevelHandler implements LevelHandler {
    protected LevelHandler next;

    @Override
    public void setNext(LevelHandler next) {
        this.next = next;
    }

    protected Integer nextLevel(User user, LevelContext context) {
        return next != null ? next.determineLevel(user, context) : 0;
    }
}
````

Example Level Handler:
````java
public class Level1Handler extends AbstractLevelHandler {
    @Override
    public Integer determineLevel(User user, LevelContext context) {
        if (user.getReserveBalance() >= 50 && user.getReserveBalance() <= 100) {
            return 1;
        }
        return nextLevel(user, context);
    }
}

public class Level2Handler extends AbstractLevelHandler {
    @Override
    public Integer determineLevel(User user, LevelContext context) {
        if (user.getReserveBalance() >= 500 && user.getReserveBalance() <= 2000 &&
            context.getLevelACount() >= 4 && context.getLevelBCount() + context.getLevelCCount() >= 5) {
            return 2;
        }
        return nextLevel(user, context);
    }
}
````

Build the chain:
````java
public class LevelService {
    private final LevelHandler levelChain;

    public LevelService() {
        Level1Handler level1 = new Level1Handler();
        Level2Handler level2 = new Level2Handler();
        Level3Handler level3 = new Level3Handler();
        Level4Handler level4 = new Level4Handler();

        level1.setNext(level2);
        level2.setNext(level3);
        level3.setNext(level4);

        this.levelChain = level1;
    }

    public int determineLevel(User user) {
        LevelContext context = new LevelContext(user, user.getId()); // precompute counts here
        return levelChain.determineLevel(user, context);
    }
}
````

Precomputed counts context:
````java
public class LevelContext {
    private final int levelACount;
    private final int levelBCount;
    private final int levelCCount;

    public LevelContext(User user, Long userId) {
        this.levelACount = calculateLevelACount(userId);
        this.levelBCount = calculateLevelBCount(userId);
        this.levelCCount = calculateLevelCCount(userId);
    }

    // getters
}
````


## 🧱 Alternative: Strategy Pattern (if levels are mutually exclusive strategies)
- Less dynamic than the chain but good if you know you'll always use only one strategy based on a config/rule.
- Each level rule becomes a strategy class.




## ✅ Decorator Pattern
The Decorator Pattern is used to dynamically add responsibilities to an object at runtime. It's ideal when you want to compose behavior in a flexible way without changing the object’s core class.

## ❌ Why It's Not Ideal for determineLevel
In your case:
- Level determination is exclusive: only one level applies at a time (you don’t “add” levels; you pick one).
- It’s a decision-making chain, not a behavior-composition stack.
- Each level depends on conditions, not on building up functionality.

Using decorators here would feel forced or unnatural. For example:
````java
LevelCalculator base = new Level0Calculator();
LevelCalculator level1 = new Level1Decorator(base);
LevelCalculator level2 = new Level2Decorator(level1);
...
int level = level4.determineLevel(user, context);
````

✅ Where Decorator Might Fit in Your System
While it's not ideal for determineLevel, Decorator could be useful elsewhere, such as:
- **Bonus calculation**: e.g., add special bonus rules based on user role, day of the week, promotions, etc.
- **Commission calculation**: if you want to layer extra commission rules conditionally.
- **Auditing or logging enhancements** to your services without modifying their core logic.


## 2. Best Design Pattern for calculateLevelACount, calculateLevelBCount etc... 
Use a shared mutable context object (e.g., `LevelCalculationContext`) that holds intermediate results (counts and IDs). Each `calculateLevelXCount` method updates and reads from this shared object.

Step 1: Define a Context Holder
````java
@Data
public class LevelCalculationContext {
    private Long userId;
    private List<Long> levelAIds;
    private List<Long> levelBIds;
    private List<Long> levelCIds;
    private int levelACount;
    private int levelBCount;
    private int levelCCount;
}
````

Step 2: LevelCountTemplate
````java
public abstract class LevelCountTemplate {

    public final LevelCounts calculateAllLevels(Long userId) {
        LevelCalculationContext context = new LevelCalculationContext();
        context.setUserId(userId);

        calculateLevelACount(context);
        calculateLevelBCount(context);
        calculateLevelCCount(context);

        return new LevelCounts(
            context.getLevelACount(),
            context.getLevelBCount(),
            context.getLevelCCount()
        );
    }

    protected abstract void calculateLevelACount(LevelCalculationContext context);
    protected abstract void calculateLevelBCount(LevelCalculationContext context);
    protected abstract void calculateLevelCCount(LevelCalculationContext context);
}
````

Step 3: Implement Efficient Logic in Subclass
````java
@RequiredArgsConstructor
public class DefaultLevelCountService extends LevelCountTemplate {

    private final UserHierarchyRepository userHierarchyRepository;

    @Override
    protected void calculateLevelACount(LevelCalculationContext context) {
        List<Long> levelAIds = userHierarchyRepository.findByAncestorAndDepth(context.getUserId(), 1)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        context.setLevelAIds(levelAIds);
        context.setLevelACount(levelAIds.size());
    }

    @Override
    protected void calculateLevelBCount(LevelCalculationContext context) {
        List<Long> levelAIds = context.getLevelAIds(); // reused
        List<Long> levelBIds = userHierarchyRepository.findByAncestorsAndDepth(levelAIds, 2)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        context.setLevelBIds(levelBIds);
        context.setLevelBCount(levelBIds.size());
    }

    @Override
    protected void calculateLevelCCount(LevelCalculationContext context) {
        List<Long> levelBIds = context.getLevelBIds(); // reused
        List<Long> levelCIds = userHierarchyRepository.findByAncestorsAndDepth(levelBIds, 3)
                .stream()
                .map(UserHierarchy::getDescendant)
                .toList();

        context.setLevelCIds(levelCIds);
        context.setLevelCCount(levelCIds.size());
    }
}
````

````java
public record LevelCounts(
    int levelACount,
    int levelBCount,
    int levelCCount
) {}
````