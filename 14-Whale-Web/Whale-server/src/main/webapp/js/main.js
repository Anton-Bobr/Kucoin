Date.prototype.toDateInputValue = (function() {
  let local = new Date(this);
  local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
  return local.toJSON().slice(0,16);
});

document.getElementById('date_second').max = new Date().toDateInputValue();
document.getElementById('date_second').value = new Date().toDateInputValue();
document.getElementById('date_first').max = document.getElementById('date_second').value

const dateFirst = document.getElementById('date_first');

dateFirst.onclick = function () {
  document.getElementById('date_first').max = document.getElementById('date_second').value;
  console.log(document.getElementById('date_first').value);
  }

let dateSecond = document.getElementById('date_second');

dateSecond.onclick = function () {
  if (document.getElementById('date_second').value < document.getElementById('date_first').value ||
    document.getElementById('date_first').value === "") {
    document.getElementById('date_first').value = document.getElementById('date_second').value ;
  }
  console.log("date_first =(" + document.getElementById('date_first').value + ")");
}


const buttonGetCoins = document.getElementById('button_get_coins')

buttonGetCoins.onclick = async function () {
  let requestURL = 'http://localhost:8081/Whale-server-1.0/checkCoins';
  let responseGetCoin = await fetch(requestURL);
  let jsonGetCoin;
  if (responseGetCoin.ok) {
    jsonGetCoin = await responseGetCoin.json();
  } else {
    alert("Error HTTP" + responseGetCoin.status);
    console.log("Error HTTP" + responseGetCoin.status);
  }
  let parentElement = document.getElementById('coin_checkbox');
  while (parentElement.firstChild) {
    parentElement.removeChild(parentElement.firstChild);
  }
  jsonGetCoin.forEach(coins => {
    let element_div = document.createElement('div')
    element_div.id = 'div_coin';
    element_div.className = 'checkbox_coin'

    let element_input = document.createElement('input');
    element_input.type = 'checkbox' ;
    element_input.id = coins.coin;
    element_input.className = 'coin_input'
    element_input.checked = false;

    let element_label = document.createElement('label')
    element_label.for = coins.coin;
    element_label.textContent = coins.coin;

    parentElement.appendChild(element_div);
    element_div.appendChild(element_input);
    element_div.appendChild(element_label);
  })
}

const buttonSelectAll = document.getElementById('select_all')

buttonSelectAll.onclick = async function () {
  let buttonStatus;

  if (document.getElementById('select_all').textContent === 'SELECT ALL') {
    buttonStatus = true
    document.getElementById('select_all').textContent = 'CLEAR ALL'
  } else {
    buttonStatus = false
    document.getElementById('select_all').textContent = 'SELECT ALL'
  }
  let myElements = document.getElementsByClassName('coin_input');
  if (myElements != null) {
    for (let i = 0; i <= myElements.length - 1; i++) {
      myElements[i].checked = buttonStatus;
    }
  }
}


const buttonGetData = document.getElementById('get_data')

buttonGetData.onclick = async function () {

  let parentElement = document.getElementById('grid_table');
  while (parentElement.firstChild) {
    parentElement.removeChild(parentElement.firstChild);
  }

  let dateStart = document.getElementById('date_first').value;
  let dateStop = document.getElementById('date_second').value;
  let checkedCoins = checkedCoinsArray()

  function checkedCoinsArray () {
    let myElements = document.getElementsByClassName('coin_input');
    let checkedCoinsArray = [];
    if (myElements != null) {
      for (let i = 0; i <= myElements.length - 1; i++) {
        if (myElements[i].checked !== false) {
          checkedCoinsArray.push(myElements[i].id);
        }
      }
    }
    return checkedCoinsArray;
  }

  let requestURL = 'http://localhost:8081/Whale-server-1.0/checkWhaleMessages?dateStart='
    + dateStart.toString() + '&dateStop=' + dateStop.toString() + '&coins=' + checkedCoins.join('-');
  let responsePostWhaleMessages = await fetch(requestURL);
  let jsonPostWhaleMessages;

  if (responsePostWhaleMessages.ok) {
    jsonPostWhaleMessages = await responsePostWhaleMessages.json();
  } else {
    alert("Error HTTP" + responsePostWhaleMessages.status);
    console.log("Error HTTP" + responsePostWhaleMessages.status);
  }

  let jsonKeysArray = Object.keys(jsonPostWhaleMessages[0]);

  let table = document.createElement('table');
  table.align = 'center'
  table.border = '5'
  let thead = document.createElement('thead');
  let tbody = document.createElement('tbody');
  let rowHead = document.createElement('tr');

  jsonKeysArray.forEach(jsonKey => {
    let tableColumn = document.createElement('th');
    tableColumn.textContent = jsonKey.toString();
    rowHead.appendChild(tableColumn);
  })
  thead.appendChild(rowHead);
  table.appendChild(thead);


  for (let i = 0; i <= jsonPostWhaleMessages.length - 1; i++) {
    let rowBody = document.createElement('tr');
    for (let j = 0; j <= jsonKeysArray.length - 1; j++) {
      let tableColumn = document.createElement('td');
      tableColumn.textContent = jsonPostWhaleMessages[i][jsonKeysArray[j]];
      rowBody.appendChild(tableColumn);
    }
    tbody.appendChild(rowBody);
    table.appendChild(tbody);
  }
  parentElement.appendChild(table);
}

