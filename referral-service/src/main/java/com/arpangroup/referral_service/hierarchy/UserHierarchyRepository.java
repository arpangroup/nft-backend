package com.arpangroup.referral_service.hierarchy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHierarchyRepository extends JpaRepository<UserHierarchy, Long> {
    List<UserHierarchy> findByAncestor(Long ancestor);
    List<UserHierarchy> findByDescendant(Long descendant);

    @Query("SELECT uh FROM UserHierarchy uh WHERE uh.ancestor = :ancestor AND uh.depth = :depth")
    List<UserHierarchy> findByAncestorAndDepth(Long ancestor, int depth);
}
