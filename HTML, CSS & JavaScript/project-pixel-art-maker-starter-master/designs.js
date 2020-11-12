const myTable = document.getElementById("pixelCanvas");

// Select color input
const color = "rgb(239, 239, 239)";

// Styling submit button
const submitButton = document.querySelector('input[type="submit"]');
submitButton.style.borderRadius = "6px";
submitButton.style.borderColor = color;

// Select size input
//Get the value of the input rows
const selectedHeight = document.getElementById("inputHeight");

//Get the value of te input cells
const selectedWidth  = document.getElementById("inputWidth");


// When size is submitted by the user, call makeGrid()
submitButton.addEventListener("click", function(e) {
    //check if the table is empty
    if (myTable.rows.length == 0){
        makeGrid()
        e.preventDefault();
    } else {
        myTable.innerHTML = "";
        makeGrid()
        e.preventDefault();
    }
    
});


function makeGrid() {
    for (let i = 0; i < selectedHeight.value; i++) {
        var row = myTable.insertRow(i);
        for (let j = 0; j < selectedWidth.value; j++) {
            var cell = row.insertCell(j);
            cell.style.height = "50px";
            cell.style.width = "50px";
        }
    }
    clickCell();
}


function clickCell(){
    //check if the table is created
    if (myTable != null) {
        for (let i = 0; i < myTable.rows.length; i++) {
            for (let j = 0; j < myTable.rows[i].cells.length; j++){
                myTable.rows[i].cells[j].addEventListener("click", function() { 
                    changeColor(this); 
                });
            }
        }
    }
}


function changeColor(myCell) {
    //change background color of a cell
    let selectColor = document.getElementById("colorPicker").value;
    if (myCell.style.backgroundColor == "") {
        myCell.style.backgroundColor = selectColor;
   } else {
       myCell.style.backgroundColor = "";
   }
}

