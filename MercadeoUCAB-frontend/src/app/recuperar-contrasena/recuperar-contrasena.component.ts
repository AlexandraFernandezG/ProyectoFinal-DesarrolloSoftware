import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationsService } from 'angular2-notifications';
import { Usuario } from 'src/app/modelos/usuario';
import { Usuario2 } from 'src/app/modelos/usuario';
import { Rol } from '../modelos/rol';
import { UsuariosService } from '../servicios/usuarios.service';

@Component({
  selector: 'app-recuperar-contrasena',
  templateUrl: './recuperar-contrasena.component.html',
  styleUrls: ['./recuperar-contrasena.component.css']
})
export class RecuperarContrasenaComponent implements OnInit {
  envioForm: FormGroup;
  error: {};

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private service: UsuariosService,
    public actRoute: ActivatedRoute,
    private servicenotifications: NotificationsService
  ) {

  this.envioForm = this.fb.group({
    correo: new FormControl ('', [Validators.required, Validators.minLength(6)])

  });
 }

usuario: Usuario = {
  _id: 1 ,
  _nombre: '',
  _correoelectronico: '',
  _codigoRecuperacion: '',
  _estatus: 'Activo',
  _rol: {
    _id: 0,
    _nombre: '',
    _estatus: ''
  }
};

roles: Rol[] = [
];
usuarios: any;
usuarioForm: FormGroup;

  ngOnInit(): void {
    this.envioForm = new FormGroup(
      {
        correo: new FormControl('', [Validators.required, Validators.minLength(6)])
      }
    );
  }

  onSucess(message){
    this.servicenotifications.success('Exito', message, {
      position: ['bottom', 'right'],
      timeOut: 2000,
      animate: 'fade',
      showProgressBar: true,
      })
  }

  envioPassword( usuario: Usuario): void {
   
	 const envioPa: Usuario2 = {
    id: this.usuario._id,
    nombreUsuario: this.usuario._nombre,
    correo: this.envioForm.get('correo').value,
    codigoRecuperacion: '',
    contrasena: '',
    estatus: 'Activo',
    rol: usuario._rol._id
  };
    this.service.enviarClave(envioPa).subscribe();
    this.onSucess('Envio de contrasena exitoso, revise su correo...');
    setTimeout(() => {
      this.router.navigate(['/login']);
    },2000);

  }

  atras(){
    this.router.navigate(['/login']);
  }

}
