<template>
  <div class="bg-zinc-900 h-screen flex">
    <!-- Sidebar -->
    <div
      class="bg-black w-[330px] p-8 flex flex-col overflow-y-auto notes-list"
    >
      <div>
        <Logo />
      </div>
      <!-- Today -->
      <div>
        <div class="flex grow">
          <p class="text-xs font-bold text-[#C2C2C5] mt-12 mb-4">Today</p>
        </div>
        <div class="ml-2 space-y-2">
          <div
            v-for="note in todayNotes"
            class="p-2 rounded-lg cursor-pointer"
            :class="{
              'bg-[#A1842C]': note.id === selectedNote.id,
              'hover:bg-[#A1842C]/50': note.id != selectedNote.id,
            }"
            @click="selectedNote = note"
          >
            <h3 class="text-sm font-bold text-[#D6D6D6]">
              <!-- Just Finish Reading... -->
              {{ note.text.substring(0, 50) }}
            </h3>
            <div class="leading-none truncate text-[#C2C2C5]">
              <span class="text-xs text-[#F4F4F5] mr-4">
                {{ formatDate(note.updateAt) }}</span
              >
              <span v-if="note.text > 50" class="text-xs text-[#C2C2C5]"
                >...{{ note.text.substring(50, 100) }}</span
              >
            </div>
          </div>
        </div>
      </div>
      <!-- /Today -->

      <!-- Yesterday -->
      <div>
        <div>
          <p class="text-xs font-bold text-[#C2C2C5] mt-12 mb-4">Yesterday</p>
        </div>
        <div class="ml-2 space-y-2">
          <div
            v-for="note in yesterdayNotes"
            class="p-2 rounded-lg cursor-pointer"
            :class="{
              'bg-[#A1842C]': note.id === selectedNote.id,
              'hover:bg-[#A1842C]/50': note.id != selectedNote.id,
            }"
            @click="selectedNote = note"
          >
            <h3 class="text-sm font-bold text-[#D6D6D6]">
              <!-- Just Finish Reading... -->
              {{ note.text.substring(0, 50) }}
            </h3>
            <div class="leading-none truncate text-[#C2C2C5]">
              <span class="text-xs text-[#F4F4F5] mr-4">
                {{ formatDate(note.updateAt) }}</span
              >
              <span class="text-xs text-[#C2C2C5]"
                >...{{ note.text.substring(50, 100) }}</span
              >
            </div>
          </div>
        </div>
      </div>
      <!-- /Yesterday -->

      <!-- earliest -->
      <div>
        <div>
          <p class="text-xs font-bold text-[#C2C2C5] mt-12 mb-4">Earliest</p>
        </div>
        <div class="ml-2 space-y-2">
          <div
            v-for="note in earlierNotes"
            class="p-2 rounded-lg cursor-pointer"
            :class="{
              'bg-[#A1842C]': note.id === selectedNote.id,
              'hover:bg-[#A1842C]/50': note.id != selectedNote.id,
            }"
            @click="selectedNote = note"
          >
            <h3 class="text-sm font-bold text-[#D6D6D6]">
              <!-- Just Finish Reading... -->
              {{ note.text.substring(0, 50) }}
            </h3>
            <div class="leading-none truncate text-[#C2C2C5]">
              <span class="text-xs text-[#F4F4F5] mr-4">
                {{ formatDate(note.updateAt) }}</span
              >
              <span class="text-xs text-[#C2C2C5]"
                >...{{ note.text.substring(50, 100) }}</span
              >
            </div>
          </div>
        </div>
      </div>
      <!-- /earliest -->
    </div>
    <!-- /sidebar -->

    <!-- Note information -->
    <div class="w-full flex flex-col">
      <div class="flex justify-between w-full items-start p-8">
        <button
          class="text-xs inline-flex items-center text-[#C2C2C5] font-bold space-x-1 hover:text-white cursor-pointer"
          @click="createNote"
        >
          <PencilIcon />
          <span>Create Note</span>
        </button>
        <button @click="deleteNote">
          <TrashIcon class="cursor-pointer text-[#6D6D73] hover:text-white" />
        </button>
      </div>

      <div class="max-w-[437px] mx-auto w-full grow flex flex-col">
        <p class="text-[#6D6D73] font-playfair hover:text-white">
          {{ formatDate(selectedNote.updateAt) }}
        </p>

        <textarea
          v-model="updatedNote"
          class="text-[#D4D4D4] my-4 font-playfair w-full bg-transparent focus: resize-none grow overflow-y-auto note-editor"
          @input="onNoteInput"
        ></textarea>
      </div>
      <button
        class="text-zinc-400 hover:text-white cursor-pointer text-sm font-bold absolute right-0 bottom-0 p-8"
        @click="logout"
      >
        Logout
      </button>
    </div>
    <!-- /Note information -->
  </div>
</template>

<script setup>
const notes = ref([]);
const selectedNote = ref({});
const todayNotes = ref([]);
const yesterdayNotes = ref([]);
const earlierNotes = ref([]);

const updatedNote = ref("");
onMounted(async () => {
  // fetch notes
  notes.value = await api("/api/v1/notes");
  splitNotes();
  // set the first note as selected
  if (notes.value.length > 0) {
    selectedNote.value = notes.value[0];
    updatedNote.value = selectedNote.value.text;
  }

  console.log(notes.value);
});

async function updateNote() {
  if (!selectedNote.value?.id) {
    console.warn("Update skipped: no selected note id");
    return;
  }
  try {
    await api(`/api/v1/notes/${selectedNote.value.id}`, {
      method: "PATCH",
      body: {
        text: updatedNote.value,
      },
    });

    // also update in notes list
    const index = notes.value.findIndex((n) => n.id === selectedNote.value.id);
    if (index !== -1) {
      notes.value[index].text = updatedNote.value;
    }
  } catch (err) {
    console.log(err);
  }
}

watch(selectedNote, (note) => {
  if (note && note.text !== undefined) {
    updatedNote.value = note.text;
  }
});

function formatDate(date) {
  const d = new Date(date);
  const today = new Date();

  if (d.toDateString() === today.toDateString()) {
    return "Today";
  }
  return d.toLocaleDateString();
  // function Spliting Notes based on date
}

function splitNotes() {
  const today = new Date();
  const yesterday = new Date();
  yesterday.setDate(today.getDate() - 1);

  todayNotes.value = [];
  yesterdayNotes.value = [];
  earlierNotes.value = [];

  notes.value.forEach((note) => {
    const noteDay = new Date(note.updateAt); // from java date to javascript date
    const formattedNoteDay = noteDay.toDateString();

    if (formattedNoteDay === today.toDateString()) {
      todayNotes.value.push(note);
    } else if (formattedNoteDay === yesterday.toDateString()) {
      yesterdayNotes.value.push(note);
    } else {
      earlierNotes.value.push(note);
    }
  });
}

let debounceTimer = null;

function onNoteInput() {
  // cancel previous scheduled save
  clearTimeout(debounceTimer);

  // schedule a new save
  debounceTimer = setTimeout(() => {
    updateNote();
  }, 600); // 600ms delay
}

async function createNote() {
  try {
    const newNote = await api("/api/v1/notes", {
      method: "POST",
      body: {
        text: "New note",
      },
    });

    notes.value.unshift(newNote);

    splitNotes();

    selectedNote.value = newNote;
    updatedNote.value = newNote.text;
  } catch (err) {
    console.log(err);
  }
}

async function deleteNote() {
  if (!selectedNote.value?.id) return;

  try {
    await api(`/api/v1/notes/${selectedNote.value.id}`, {
      method: "DELETE",
    });

    removeFromLocalState(selectedNote.value.id);
  } catch (err) {
    console.log(err);
  }
}

function removeFromLocalState(noteId) {
  notes.value = notes.value.filter((n) => n.id != noteId);
  // re-split grouped notes
  splitNotes();
  // handle selection
  if (notes.value.length > 0) {
    selectedNote.value = notes.value[0];
    updatedNote.value = selectedNote.value.text;
  } else {
    // no notes left
    selectedNote.value = {};
    updatedNote.value = "";
  }
}
async function logout() {
  localStorage.removeItem("token");
  navigateTo("/login");
}
</script>

<!-- From the Backend the data is coming in the form of List 
     Store the data into the ref
     const res = await api("/api/v1/notes"); -> notes.value
     store it inside the ref const -->

<style scoped>
.notes-list::-webkit-scrollbar {
  width: 6px;
}

.notes-list::-webkit-scrollbar-track {
  background: transparent;
}

.notes-list::-webkit-scrollbar-thumb {
  background-color: #6b5a1f;
  border-radius: 10px;
}

.notes-list::-webkit-scrollbar-thumb:hover {
  background-color: #a98b2b;
}

/* Firefox */
.notes-list {
  scrollbar-width: thin;
  scrollbar-color: #6b5a1f transparent;
}

.notes-list::-webkit-scrollbar-thumb {
  background-color: transparent;
}

.notes-list:hover::-webkit-scrollbar-thumb {
  background-color: #6b5a1f;
}

/* ===== NOTE EDITOR SCROLLBAR ===== */

/* Chrome, Edge, Safari */
.note-editor::-webkit-scrollbar {
  width: 8px;
}

.note-editor::-webkit-scrollbar-track {
  background: transparent;
}

.note-editor::-webkit-scrollbar-thumb {
  background-color: #2e2e2e;
  border-radius: 10px;
}

.note-editor::-webkit-scrollbar-thumb:hover {
  background-color: #6b5a1f; /* gold hover */
}

/* Firefox */
.note-editor {
  scrollbar-width: thin;
  scrollbar-color: #2e2e2e transparent;
}
</style>
