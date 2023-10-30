function displayContent() {
			var transtype = document.getElementById("transtype").value;
			var application = document.getElementById("application").value;
			var direction = document.getElementById("direction").value;
			var cNumber = document.getElementById("searchInput").value;
			
			var businessProcess=document.getElementById("businessProcess").value;
			var businessProcess2=document.getElementById("businessProcess2").value;
			var contentDisplay = document.getElementById("contentDisplay");

			var mailboxContent, mailboxContent2;
			var routingRuleContent,routingRuleContent2;
			var businessProcess = "Business process:- "+businessProcess;
		
		var businessProcess2 = "Business process:- "+businessProcess2;

			if (application == null || application ==="") {
				mailboxContent = "MailBox:- " + "/" + direction + "/" + transtype + "/" + cNumber;
				routingRuleContent = "Routing Rule:- " + direction + "_" + transtype + "_" + cNumber;
			} else {
				mailboxContent = "MailBox:- " + "/" + direction + "/" + transtype + "/" + cNumber;
				mailboxContent2 = "MailBox:- " + "/" + direction + "/" + application + "/" + cNumber;
				routingRuleContent = "Routing Rule:- " + direction + "_" + transtype + "_" + cNumber;
				routingRuleContent2 = "Routing Rule:- " + direction + "_" + application + "_" + cNumber;
			}

		 //routingRuleContent = "Routing Rule:- " + direction + "_" + transtype + "_" + cNumber;
			

			contentDisplay.innerHTML = mailboxContent + "<br>" + routingRuleContent + "<br>"+ businessProcess;

			if (mailboxContent2) {
				contentDisplay.innerHTML += "<br>" + mailboxContent2+"<br>"+ routingRuleContent2+ "<br>"+ businessProcess2;
			}
		}
		
		
function saveDataToFile() {
	
	var content = document.getElementById("contentDisplay").textContent;
	var data = content.split("\n").join("\n");

	// Create a Blob with the text content
	var blob = new Blob([data], { type: "text/plain" });

	// Create a URL for the Blob
	var url = window.URL.createObjectURL(blob);

	// Create a link element for downloading the file
	var a = document.createElement("a");
	a.style.display = "none";
	a.href = url;
	a.download = "loadstar.txt"; // Specify the filename

	// Trigger a click event on the link to initiate the download
	document.body.appendChild(a);
	a.click();

	// Clean up
	window.URL.revokeObjectURL(url);
	document.body.removeChild(a);
}		
		
		