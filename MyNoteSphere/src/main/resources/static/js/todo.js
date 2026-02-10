document.addEventListener("DOMContentLoaded", loadTodos);

function loadTodos() {
    fetch("/api/todos")
        .then(response => response.json())
        .then(data => {
            console.log("GET /api/todos response:", data);

            // ✅ force array handling
            if (Array.isArray(data)) {
                renderTodos(data);
            } else if (data && Array.isArray(data.data)) {
                renderTodos(data.data);
            } else if (data && Array.isArray(data.todos)) {
                renderTodos(data.todos);
            } else {
                console.error("Expected array but got:", data);
                renderTodos([]);
            }
        })
        .catch(err => console.error("Load todos error:", err));
}


function renderTodos(todos) {
    if (!Array.isArray(todos)) {
        console.error("renderTodos expected array, got:", todos);
        return;
    }

    const list = document.getElementById("todoList");
    list.innerHTML = "";

    todos.forEach(todo => {
        const li = document.createElement("li");

        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = todo.completed;
        checkbox.onchange = () => toggleTodo(todo.id);

        const span = document.createElement("span");
        span.textContent = todo.title;
        if (todo.completed) {
            span.style.textDecoration = "line-through";
        }

        const deleteBtn = document.createElement("button");
        deleteBtn.textContent = "X";
        deleteBtn.onclick = () => deleteTodo(todo.id);

        li.appendChild(checkbox);
        li.appendChild(span);
        li.appendChild(deleteBtn);

        list.appendChild(li);
    });
}


function addTodo() {
    const input = document.getElementById("taskInput");
    const title = input.value.trim();

    if (!title) {
        alert("Task cannot be empty");
        return;
    }

    fetch(`/api/todos?title=${encodeURIComponent(title)}`, {
        method: "POST"
    })
        .then(response => response.json())
        .then(data => {
            console.log("POST /api/todos response:", data);
            input.value = "";
            loadTodos(); // 🔑 always reload list
        })
        .catch(err => console.error("Add todo error:", err));
}


function toggleTodo(id) {
    fetch(`/api/todos/${id}/toggle`, {
        method: "PUT"
    })
        .then(() => loadTodos());
}

function deleteTodo(id) {
    fetch(`/api/todos/${id}`, {
        method: "DELETE"
    })
        .then(() => loadTodos());
}
