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
    console.log("HTTP answer   " + jsonGetCoin.toString());

  } else {
    alert("Error HTTP" + responseGetCoin.status);
    console.log("Error HTTP" + responseGetCoin.status);
  }
  let coin = JSON.parse(jsonGetCoin);
  console.log(coin[1].toString());
  console.log("message")
}
