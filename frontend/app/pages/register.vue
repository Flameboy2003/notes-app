<template>
  <div class="bg-black h-screen flex">
    <!-- Sidebar -->
    <div class="bg-zinc-900 w-[500px] p-12 flex flex-col justify-center">
      <Logo />
      <h1 class="text-white font-bold text-lg mt-8">
        Sign up for a free account
      </h1>
      <p class="text-zinc-300 text-sm">
        Already registered? <NuxtLink
          to="/login"
          class="text-[#FFAC00] font-bold underline mt-0.5"
          >Log in </NuxtLink
        >to your account
      </p>

      <form @submit.prevent="register">
        <!-- Email -->
        <div class="mt-8">
          <label for="" class="text-zinc-300 text-sm font-normal block mb-0.5"
            >Email Address</label
          >
          <input
            v-model="email"
            type="email"
            placeholder="your@example.com"
            class="block bg-[#27272A] text-white px-4 py-2 border-[#3F3F46] w-full rounded placeholder:text-zinc-500"
          />
        </div>
        <!-- /Email -->

        <!-- Password -->
        <div class="mt-6">
          <label for="" class="text-zinc-300 text-sm font-normal block mb-0.5"
            >Password</label
          >
          <input
            v-model="password"
            type="Password"
            placeholder="*********"
            class="block bg-[#27272A] text-white px-4 py-2 border-[#3F3F46] w-full rounded placeholder:text-zinc-500"
          />
        </div>
        <!-- /Password -->

        <!-- Sign Up Button -->
        <div>
          <button
            class="w-full mt-4 cursor-pointer bg-[#FFAC00] rounded-full px-4 py-2 text-zinc-800 text-sm font-bold flex justify-center items-center space-x-2"
          >
            <span>Sign Up</span> <Arrow />
          </button>
        </div>
        <!-- /Sign Up Button -->
      </form>
    </div>
    <!-- /sidebar -->

    <!-- Note information -->
    <div class=""></div>
    <!-- /Note information -->
  </div>
</template>

<script setup>
import { ref } from "vue";
import validator from "validator";

const email = ref("");
const password = ref("");

const register = async () => {
  if (!validator.isEmail(email.value)) {
    console.error("Invalid Email");
    return;
  }

  try {
    const res = $fetch("http://localhost:8080/api/v1/register", {
      method: "POST",
      body: { email: email.value, password: password.value },
    });
  } catch (err) {
    console.log(err);
  }
};
</script>
