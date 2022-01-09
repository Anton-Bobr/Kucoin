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
  if (responseGetCoin.ok) {
    let answer = await responseGetCoin.toString()
    //let jsonGetCoin = await responseGetCoin.json();
    //console.log("HTTP answer   " + jsonGetCoin.toString());
    console.log("HTTP answer   " + answer);

  } else {
    alert("Error HTTP" + responseGetCoin.status);
    console.log("Error HTTP" + responseGetCoin.status);
  }
}
