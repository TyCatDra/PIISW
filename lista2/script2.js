const form = document.getElementById("packageForm");
const tableBody = document.getElementById("tableBody");
const totalVolumeCell = document.getElementById("totalVolume");
const searchInput = document.getElementById("search");
let totalVolume = 0;
form.addEventListener("submit", function(e){
    e.preventDefault();
    if(!form.checkValidity()){
        form.classList.add("was-validated");
        return;
    }
    const name = document.getElementById("name").value;
    const width = parseInt(document.getElementById("width").value);
    const height = parseInt(document.getElementById("height").value);
    const depth = parseInt(document.getElementById("depth").value);
    let volume = (width * height * depth) / 1000000;
    volume = volume.toFixed(2);
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${name}</td>
        <td>${width}</td>
        <td>${height}</td>
        <td>${depth}</td>
        <td>${volume}</td>
        `;
    tableBody.appendChild(row);
    totalVolume += parseFloat(volume);
    totalVolumeCell.textContent = totalVolume.toFixed(2);
    form.reset();
    form.classList.remove("was-validated");
});
searchInput.addEventListener("keyup", function(){
    const filter = searchInput.value.toLowerCase();
    const rows = tableBody.getElementsByTagName("tr");
    for(let i=0;i<rows.length;i++){
        let text = rows[i].textContent.toLowerCase();
        rows[i].style.display =
        text.includes(filter) ? "" : "none";
    }
});