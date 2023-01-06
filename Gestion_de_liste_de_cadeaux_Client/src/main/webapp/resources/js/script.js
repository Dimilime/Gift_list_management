const searchInput = document.getElementById("search");
const searchResult = document.getElementById("result");
const copyLinkInput = document.getElementById("copyLinkInput");
const copyLinkBtn = document.getElementById("copyLinkBtn");

let dataArray;

copyLinkInput.addEventListener("focus", () => copyLinkInput.select());
copyLinkBtn.addEventListener("click", () => {
	const link = copyLinkInput.value;

	copyLinkInput.select();
	navigator.clipboard.writeText(link);

	copyLinkInput.value = "Copié !";
	setTimeout(() => copyLinkInput.value = link, 2000);
});

(function getUsers() {
	var xhr = new XMLHttpRequest()
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			searchResult.innerHTML= "";
			readData(xhr.responseText);//responseText is users
		}
		else {
			searchResult.innerHTML= "<h3>Chargement ...</h3>";
		}
	};
	xhr.open("GET", "http://localhost:8080/Gestion_de_liste_de_cadeaux_API/api/user", true);
	xhr.setRequestHeader("key", "B61UGnAzniOU1NZc5jqZgC17M838GB1MczxyhR7eTx4oPDm5YSCjYklsN98wkpnU6dCDXf4QZ29xbTsFIy9F5ISeDZJpXSgVHukfNRsc6dUTmi2op0WVlSmsebga4jiX")
	xhr.send();
})();



function readData(data) {
	dataArray = JSON.parse(data);
	localStorage.setItem("dataArray", JSON.parse(data));
	createUserList(dataArray);
}
function createUserList(usersList) {

	usersList.forEach(user => {

		const listItem = document.createElement("tr");
		listItem.innerHTML = '<td>' + user.lastname + '</td>'
			+ '	<td>' + user.firstname + '</td>'
			+ '	<td>' + user.email + '</td>'
			+ '	<td>'
			+ `<form action="shareList" method="post" >
					<button class="btn btn-secondary" type="submit" name="userEmail" value="${user.email}">Partager</button>
			   </form>`
			+ '	</td>';

		searchResult.appendChild(listItem)
	})

}

searchInput.addEventListener("input", filterData);

function filterData(e) {

	searchResult.innerHTML = "";
	var searchedString = e.target.value.toLowerCase().replace(/\s/g, "");// enlève les espaces

	var filteredArr = dataArray.filter(u => u.firstname.toLowerCase().includes(searchedString)//si ce qu'on cherche se trouve dans le prenom ou le nom alors on le retient
		|| u.lastname.toLowerCase().includes(searchedString)
		|| `${u.lastname + u.firstname}`.toLowerCase().replace(/\s/g, "").includes(searchedString)
		|| `${u.firstname + u.lastname}`.toLowerCase().replace(/\s/g, "").includes(searchedString)
	);

	createUserList(filteredArr);
}






