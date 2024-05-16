<template>
    <div>
        <h1>Login</h1>
        <form @submit.prevent="login">
            <label for="product name">product name:</label>
            <input type="text" id="productname">
            <label for="product description">product description</label>
            <input type="text" id="productdescription">
            <button type="submit">Login</button>
        </form>
    </div>
</template>