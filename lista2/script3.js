class Note{
    constructor(id, title, content){
        this.id = id
        this.title = title
        this.content = content
    }
}
class StorageService{
    static KEY = "notesApp"
    static load(){
        const data = localStorage.getItem(this.KEY)
        return data ? JSON.parse(data) : []
    }
    static save(notes){
        localStorage.setItem(this.KEY, JSON.stringify(notes))
    }
}
class NotesApp{
    constructor(){
        this.notes = []
        this.currentNoteId = null

        this.list = document.getElementById("notesList")
        this.form = document.getElementById("noteForm")
        this.title = document.getElementById("title")
        this.content = document.getElementById("content")
        this.exportBtn = document.getElementById("exportBtn")
        this.importFile = document.getElementById("importFile")

        this.init()
    }
    init(){
        this.notes = StorageService.load()
        this.renderList()
        this.form.addEventListener("submit", e => this.saveNote(e))
        this.exportBtn.addEventListener("click", () => this.exportJSON())
        this.importFile.addEventListener("change", (e) => this.importJSON(e))
    }
    generateId(){
        return Date.now().toString()
    }
    saveNote(e){
        e.preventDefault()
        const title = this.title.value.trim()
        if(!title) return
        const content = this.content.value
        if(this.currentNoteId){
            const note = this.notes.find(n => n.id === this.currentNoteId)
            note.title = title
            note.content = content
        }
        else{
            const note = new Note(this.generateId(), title, content)
            this.notes.push(note)
        }
        StorageService.save(this.notes)
        this.renderList()
        this.resetForm()
    }
    resetForm(){
        this.currentNoteId = null
        this.form.reset()
    }
    selectNote(id){
        const note = this.notes.find(n => n.id === id)
        this.title.value = note.title
        this.content.value = note.content
        this.currentNoteId = id
    }
    renderList(){
        this.list.innerHTML=""
        this.notes.forEach(note => {
            const li = document.createElement("li")
            li.className = "list-group-item note-item"
            li.textContent = note.title
            li.onclick=() => this.selectNote(note.id)
            this.list.appendChild(li)
        })
    }
    exportJSON(){
        const data = JSON.stringify(this.notes, null, 2)
        const blob = new Blob([data], {type:"application/json"})
        const url = URL.createObjectURL(blob)
        const a = document.createElement("a")
        a.href = url
        a.download = "notes.json"
        a.click()
    }
    importJSON(event){
        const file = event.target.files[0]
        if(!file) return
        const reader = new FileReader()
        reader.onload = (e) => {
            const imported = JSON.parse(e.target.result)
            this.notes = imported
            StorageService.save(this.notes)
            this.renderList()
        }
        reader.readAsText(file)
    }
}
new NotesApp()