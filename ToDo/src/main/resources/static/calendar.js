let today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();
let displayedDate = today;//The date that has been clicked on. By default is current day.

let months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

let selectedTd;

let monthAndYear = document.getElementById("monthAndYear");

showCalendar(currentMonth, currentYear);


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
				let trueMonth = month + 1;//Because months start at 0, i need to +1 to compare.
				let dateString = year + "-" + trueMonth.toString().padStart(2, 0) + "-" + date.toString().padStart(2, 0);
				let cell = document.createElement("td");
				let cellText = document.createTextNode(date);

				if (date === today.getDate() && year === today.getFullYear() && month === today.getMonth()) {
					cell.classList.add("bg-primary");
					showDisplay(dateString);
				} // color today's date

				//Check for dueDates matching with calendar dates and color them.
				for (let i = 0; i < projects.length; i++) {
					if (dateString == projects[i].dueDate)
						cell.classList.add("bg-danger");
				}
				for (let j = 0; j < tasks.length; j++) {
					if (dateString == tasks[j].dueDate && !cell.classList.contains('bg-danger'))
						cell.classList.add("bg-warning");
				}

				cell.appendChild(cellText);
				row.appendChild(cell);
				date++;
			}
		}

		tbl.appendChild(row); // appending each row into calendar body.
	}
	createMouseEvent();//Allows me to be able to click on a cell
}
function createMouseEvent() {
	let table = document.getElementById("calendar");
	if (table != null) {
		for (let i = 0; i < table.rows.length; i++) {
			for (let j = 0; j < table.rows[i].cells.length; j++)
				table.rows[i].cells[j].onclick = function() {
					let target = event.target; // where was the click?
					if (target.tagName != 'TD') return; // not on TD? Then we're not interested
					let day = parseInt($(event.target).text());
					let trueMonth = currentMonth + 1;
					let dateString = currentYear + "-" + trueMonth.toString().padStart(2, 0) + "-" + day.toString().padStart(2, 0);
					showDisplay(dateString);//update the display when clicking a Date
				};
		}
	}
}
//Updates the display with the corresponding date
function showDisplay(date) {
	let titleDate = document.getElementById("dayMonthAndYear");//header of calendar
	let display = document.getElementById("display"); // body of the calendar

	// clearing previous display
	titleDate.innerHTML = "";
	display.innerHTML = "";

	titleDate.innerHTML = date;
	let check = false;



	for (let i = 0; i < projects.length; i++) {
		if (projects[i].dueDate == date) {
			let node = document.createElement("LI");
			const link = document.createElement("a");
			link.href = "/projects/" + projects[i].id;
			let textNode2 = document.createTextNode(projects[i].name);
			link.appendChild(textNode2);
			link.title = projects[i].name;
			let textNode1 = document.createTextNode("Project: ");
			node.appendChild(textNode1);
			node.appendChild(link);
			display.appendChild(node);
			check = true;
		}
	}
	for (let i = 0; i < tasks.length; i++) {
		if (tasks[i].dueDate == date) {
		let node = document.createElement("LI");
			const link = document.createElement("a");
			link.href = "/tasks/" + tasks[i].id;
			let textNode2 = document.createTextNode(tasks[i].name);
			link.appendChild(textNode2);
			link.title = tasks[i].name;
			let textNode1 = document.createTextNode("Task: ");
			node.appendChild(textNode1);
			node.appendChild(link);
			display.appendChild(node);
			check = true;
		}
	}
	if (!check) {
		display.innerHTML = "None";
	}
}