function eliminar(id) {
	swal({
		title: "Esta seguro que decea eliminar?",
		text: "Una vez eliminado, no podrá recuperar este cliente!",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((OK) => {
			if (OK) {
				$.ajax({
					url:"/eliminarCliente/"+id,
					success:function (resp){
						console.log(resp);
					}
				});
				swal("¡Ok! ¡Cliente eliminado!", {
					icon: "success",
				}).then((ok)=>{
					if(ok){
						location.href="/listarCliente";
					}
				});
			} else {
				swal("¡Ok! Cliente sin eliminar!");
			}
		});

}