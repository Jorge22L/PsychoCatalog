document.addEventListener("DOMContentLoaded", () => {
    const buscador = document.getElementById("buscador");
    const tarjetas = document.querySelectorAll(".tarjeta");
    const btnMostrar = document.getElementById("btnMostrar");
    const descripciones = document.querySelectorAll(".descripcion");

    buscador.addEventListener("input", () => {
        const texto = buscador.value.toLowerCase();

        tarjetas.forEach(tarjeta => {
            const nombre = tarjeta.querySelector("h2").textContent.toLowerCase();
            const categoria = tarjeta.querySelector(".categoria").textContent.toLowerCase();

            if (nombre.includes(texto) || categoria.includes(texto)) {
                tarjeta.style.display = "block";
            } else {
                tarjeta.style.display = "none";
            }
        });
    });

    btnMostrar.addEventListener("click", () => {
        descripciones.forEach(descripcion => {
            descripcion.classList.toggle("oculto");
        });
    });

    tarjetas.forEach(tarjeta => {
        const boton = tarjeta.querySelector(".btn-detalle");
        boton.addEventListener("click", () => {
            alert("Aquí luego puedes mostrar el detalle del instrumento.");
        });
    });
});