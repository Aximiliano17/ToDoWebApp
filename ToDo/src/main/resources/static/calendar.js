let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let displayedDate = today;

let months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

let selectedTd;

let monthAndYear = document.getElementById("monthAndYear");
showCalendar(currentMonth, currentYear);

//create and display Calendar
function showCalendar(month, year) {

	let firstDay = (new Date(year, month)).getDay();
	let daysInMonth = 32 - new Date(year, month, 32).getDate();

	let tbl = document.getElementById("calendar-body"); // body of the calendar

	// clearing all previous cells
	tbl.innerHTML = "";

	// filing data about month and in the page via DOM.
	monthAndYear.innerHTML = months[month] + " " + year;
	currentYear.value = year;
	currentMonth.value = month;

	// creating all cells
	let date = 1;
	for (let i = 0; i < 6; i++) {
		// creates a table row
		let row = document.createElement("tr");

		//creating individual cells, filing them up with data.
		for (let j = 0; j < 7; j++) {
			if (i === 0 && j < firstDay) {
				let cell = document.createElement("td");
				let cellText = document.createTextNode("");
				cell.appendChild(cellText);
				row.appendChild(cell);
			}
			else if (date > daysInMonth) {
				break;
			}

			else {
				let cell = document.createElement("td");
				let cellText = document.createTextNode(date);
				if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
					cell.classList.add("bg-info");
				} // color today's date
				cell.appendChild(cellText);
				row.appendChild(cell);
				date++;
			}
		}

		tbl.appendChild(row); // appending each row into calendar body.
	}

	var table = document.getElementById("calendar");
	if (table != null) {
		for (var i = 0; i < table.rows.length; i++) {
			for (var j = 0; j < table.rows[i].cells.length; j++)
				table.rows[i].cells[j].onclick = function() {
					cellDisplay(event, currentMonth, currentYear);
				};
		}
	}
	function cellDisplay(event, month, year) {
		let target = event.target; // where was the click?

		if (target.tagName != 'TD') return; // not on TD? Then we're not interested
		var day = parseInt($(event.target).text());
		highlight(target);
		console.log(day, month, year);
	}

	function highlight(td) {
		if (selectedTd) { // remove the existing highlight if any
			selectedTd.classList.remove('bg-warning');
		}
		selectedTd = td;
		selectedTd.classList.add('bg-warning'); // highlight the new td
	}

	function next() {
		currentYear = (currentMonth === 11) ? currentYear + 1 : currentYear;
		currentMonth = (currentMonth + 1) % 12;
		showCalendar(currentMonth, currentYear);
	}

	function previous() {
		currentYear = (currentMonth === 0) ? currentYear - 1 : currentYear;
		currentMonth = (currentMonth === 0) ? 11 : currentMonth - 1;
		showCalendar(currentMonth, currentYear);
	}


	function showDisplay(date) {
		let display = document.getElementById("display"); // body of the calendar

		// clearing previous display
		display.innerHTML = "";

	}

}