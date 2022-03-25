//Radim Urban 2019
//Easy scripts I use mostly to adjust the webpage for mobile devices


//takes the header element and toggles its navigation display value from/to block/none
//button is only available on devices with width less than 1300px (css-defined)
//also changes the value of the button (menu/close icon)
function night(){
  var hodiny = new Date().getHours();
  if (hodiny < 10 || hodiny > 18) {
    return true;
  }
  return false;
}

function headermenu() {
  var header = document.getElementById("desktop-menu");
  var button = document.getElementById("header-menu-btn");
  var h = document.getElementsByTagName("header");
  if (header.style.display == "block") {
    button.value = "≡";
    header.style.display = "none";
    document.getElementsByTagName("header")[0].style.borderBottom = "1px solid gray";
  } else {
    button.value = "×";
    header.style.display = "block";
    document.getElementsByTagName("header")[0].style.borderBottom = "none";
  }
}

function btcbtn(){
  var adresa = document.getElementById("btca");
  if (adresa.style.display == "block") {
    adresa.style.display = "none";
  } else {
    adresa.style.display = "block";
  }
}

//changes if body uses class .dark-mode
//the class applies the dark background colour
function LightDarkMode() {
  var element = document.body;

  element.classList.toggle("dark-mode");
  var button = document.getElementById("dark-light-btn");
  if (button.value == "☀") {
    button.value = "☾";

    button.style.backgroundColor = "black";
    button.style.color = "white";

  } else {
    button.value = "☀";

    button.style.backgroundColor = "white";
    button.style.color = "black";
  }
}
