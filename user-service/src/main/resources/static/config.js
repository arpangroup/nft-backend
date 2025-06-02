
    const configState = {};

    async function loadConfigs() {
//      const response = await fetch('/api/configs');
//      const data = await response.json();
      const data = [
        {
          "key": "bonus.referral.calculation-type",
          "value": "FLAT",
          "valueType": "STRING",
          "enumValues": "FLAT, PERCENTAGE"
        },
        {
          "key": "bonus.referral.enable",
          "value": "true",
          "valueType": "BOOLEAN",
          "enumValues": null
        },
        {
          "key": "bonus.referral.flat-amount",
          "value": "300",
          "valueType": "INT",
          "enumValues": null
        },
        {
          "key": "bonus.referral.percentage-rate",
          "value": "0.5",
          "valueType": "DOUBLE",
          "enumValues": "FLAT, PERCENTAGE"
        },
        {
          "key": "bonus.signup.calculation-type",
          "value": "FLAT",
          "valueType": "STRING",
          "enumValues": "FLAT, PERCENTAGE"
        },
        {
          "key": "bonus.signup.enable",
          "value": "true",
          "valueType": "BOOLEAN",
          "enumValues": null
        },
        {
          "key": "bonus.signup.flat-amount",
          "value": "100",
          "valueType": "INT",
          "enumValues": null
        }
      ]

      const container = document.getElementById('configContainer');
      container.innerHTML = '';

      data.forEach(({ key, value, valueType, enumValues }) => {
        const div = document.createElement('div');
        div.className = 'config-item';

        const label = document.createElement('label');
        label.textContent = key;

        let inputElement;

        // Handle boolean as toggle
        if (valueType === 'BOOLEAN' || value === 'true' || value === 'false') {
          // Boolean toggle
          inputElement = document.createElement('input');
          inputElement.type = 'checkbox';
          inputElement.checked = (value === 'true');
          inputElement.onchange = () => configState[key] = inputElement.checked.toString();
        }

        // Handle enum as dropdown
        else if (enumValues && enumValues.trim().length > 0) {
            inputElement = document.createElement('select');

            enumValues.split(',').map(e => e.trim()).forEach(optionValue => {
              const option = document.createElement('option');
              option.value = optionValue;
              option.textContent = optionValue;
              if (optionValue === value) option.selected = true;
              inputElement.appendChild(option);
            });

            inputElement.onchange = () => configState[key] = inputElement.value;
        }

        // Number → Numeric input
         else if (['INT', 'FLOAT', 'DOUBLE', 'BIG_DECIMAL'].includes(valueType)) {
            inputElement = document.createElement('input');
            inputElement.type = 'number';
            inputElement.value = value;
            inputElement.step = valueType === 'INT' ? '1' : 'any';
            inputElement.oninput = () => configState[key] = inputElement.value;
          }

        // Handle everything else as text input
        else {
          // Input for numbers and strings
          inputElement = document.createElement('input');
          inputElement.type = 'text';
          inputElement.value = value;
          inputElement.oninput = () => configState[key] = input.value;
        }

        // Initial state
        configState[key] = value;

        div.appendChild(label);
        div.appendChild(inputElement);
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