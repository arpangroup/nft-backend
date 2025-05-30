
    const apiUrl = '/api/v1/config/rank';
    let currentData = []; // Store current loaded data for update reference
    let hasUnsavedChanges = false;

    function renderTable() {
      console.log("renderTable");
      const tbody = document.querySelector('#rankTable tbody');
      tbody.innerHTML = '';

      currentData.forEach(rankConfig => {
        const tr = document.createElement('tr');

        const tdRank = document.createElement('td');
        tdRank.textContent = rankConfig.rank;
        tr.appendChild(tdRank);

        const tdMin = document.createElement('td');
        const inputMin = document.createElement('input');
        inputMin.type = 'number';
        inputMin.value = rankConfig.minWalletBalance;
        inputMin.dataset.rank = rankConfig.rank;
        inputMin.dataset.field = 'minWalletBalance';
        inputMin.addEventListener('input', onInputChange);
        tdMin.appendChild(inputMin);
        tr.appendChild(tdMin);

        const tdMax = document.createElement('td');
        const inputMax = document.createElement('input');
        inputMax.type = 'number';
        inputMax.value = rankConfig.maxWalletBalance;
        inputMax.dataset.rank = rankConfig.rank;
        inputMax.dataset.field = 'maxWalletBalance';
        inputMax.addEventListener('input', onInputChange);
        tdMax.appendChild(inputMax);
        tr.appendChild(tdMax);

        for (let level = 1; level <= 3; level++) {
          const tdLevel = document.createElement('td');
          const inputLevel = document.createElement('input');
          inputLevel.type = 'number';
          inputLevel.min = 0;
          inputLevel.value = rankConfig.requiredLevelCounts?.[level] || 0;
          inputLevel.dataset.rank = rankConfig.rank;
          inputLevel.dataset.field = `requiredLevelCounts.${level}`;
          inputLevel.addEventListener('input', onInputChange);
          tdLevel.appendChild(inputLevel);
          tr.appendChild(tdLevel);
        }

        const tdProfit = document.createElement('td');
        const inputProfit = document.createElement('input');
        inputProfit.type = 'number';
        inputProfit.value = rankConfig.commissionRate;
        inputProfit.dataset.rank = rankConfig.rank;
        inputProfit.dataset.field = 'commissionRate';
        inputProfit.addEventListener('input', onInputChange);
        tdProfit.appendChild(inputProfit);
        tr.appendChild(tdProfit);


        tbody.appendChild(tr);
      });
    }


  async function loadData() {
    console.log("loadData");
    const resp = await fetch(apiUrl);
    currentData = await resp.json();
    renderTable();
    document.getElementById('updateBtn').disabled = true;
    hasUnsavedChanges = false;
  }

  function onInputChange(event) {
    const input = event.target;
    const rank = input.dataset.rank;
    const field = input.dataset.field;
    const value = parseInt(input.value);

    const rankConfig = currentData.find(r => r.rank === rank);
    if (!rankConfig) return;

    let isChanged = false;

    if (field === 'minWalletBalance') {
      isChanged = rankConfig.minWalletBalance !== value;
      rankConfig.minWalletBalance = value;
    } else if (field === 'maxWalletBalance') {
      isChanged = rankConfig.maxWalletBalance  !== value;
      rankConfig.maxWalletBalance = value;
    } else if(field === 'commissionRate') {
      isChanged = rankConfig.commissionRate !== value;
      rankConfig.commissionRate = value;
    } else if (field.startsWith('requiredLevelCounts.')) {
      const level = parseInt(field.split('.')[1]);
      if (!rankConfig.requiredLevelCounts) rankConfig.requiredLevelCounts = {};
      isChanged = rankConfig.requiredLevelCounts[level] !== value;
      rankConfig.requiredLevelCounts[level] = value;
    }

    // Highlight if changed
    if (isChanged) {
        input.classList.add('updated');
        hasUnsavedChanges = true;
        document.getElementById('updateBtn').disabled = false;
    } else {
        input.classList.remove('updated');
    }
  }

  async function updateServer() {
    console.log("UPDATE: ", currentData);

    const alertBox = document.querySelector('.alert');
    alertBox.classList.add('hidden');
    alertBox.textContent = '';

    try {
        const resp = await fetch(apiUrl, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(currentData)
        });


        if (!resp.ok) {
          const errorText = await resp.text();
          alertBox.textContent = errorText || 'Update failed.';
          alertBox.className = 'alert error';
          return;
        }

        alertBox.textContent = 'Updated successfully!';
        alertBox.className = 'alert success';
        hasUnsavedChanges = false;
        document.getElementById('updateBtn').disabled = true;
        document.querySelectorAll('.updated').forEach(el => el.classList.remove('updated'));
    } catch (e) {
        alertBox.textContent = 'Server error: ' + e.message;
        alertBox.className = 'alert error';
    }
  }

  document.getElementById('updateBtn').addEventListener('click', updateServer);

  document.getElementById('addRankBtn').addEventListener('click', () => {
      const newRankName = prompt("Enter new rank name (e.g., RANK_5):");
      if (!newRankName) return;
      if (currentData.find(r => r.rank === newRankName)) {
        alert("Rank already exists!");
        return;
      }

      const newRank = {
        rank: newRankName,
        minWalletBalance: 0,
        maxWalletBalance: 0,
        commissionRate: 0,
        requiredLevelCounts: {
          1: 0,
          2: 0,
          3: 0
        }
      };

      currentData.push(newRank);
      renderTable(); // reload UI
});

  window.onload = loadData;