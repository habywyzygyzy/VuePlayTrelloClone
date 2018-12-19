<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-danger">
        <div class="container">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#smallMobile" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <a class="navbar-brand" href="/">Kan-Ban</a>

            <div class="collapse navbar-collapse float-right" id="smallMobile">
                <button class="btn btn-outline-danger" data-toggle="modal" data-target="#addModal"><i class="text-white fas fa-plus"></i></button>
            </div>
            <span class="navbar-text">
                Drag for move sticky!
            </span>
        </div>
    </nav>
</template>

<script>
export default {
  name: 'menu'
}
</script>