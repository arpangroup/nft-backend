
    const configState = {};

    async function loadConfigs() {
//      const response = await fetch('/api/configs');
//      const data = await response.json();
      const data = {
        "bonus.threshold": "100",
        "feature.new_flow": "true",
        "commission.rate": "0.15"
      }

      const container = document.getElementById('configContainer');
      container.innerHTML = '';

      Object.entries(data).forEach(([key, value]) => {
        const div = document.createElement('div');
        div.className = 'config-item';

        const label = document.createElement('label');
        label.textContent = key;

        let input;

        if (value === 'true' || value === 'false') {
          // Boolean toggle
          input = document.createElement('input');
          input.type = 'checkbox';
          input.checked = (value === 'true');
          input.onchange = () => configState[key] = input.checked.toString();
        } else {
          // Input for numbers and strings
          input = document.createElement('input');
          input.type = 'text';
          input.value = value;
          input.oninput = () => configState[key] = input.value;
        }

        // Initial state
        configState[key] = value;

        div.appendChild(label);
        div.appendChild(input);
        container.appendChild(div);
      });
    }

    async function updateConfigs() {
      const response = await fetch('/api/configs/update', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(configState)
      });

      if (response.ok) {
        alert('Configs updated successfully');
      } else {
        alert('Failed to update configs');
      }
    }

    loadConfigs();