import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { MarcaComponent } from './admin/marca/marca.component';
import { CategoriaComponent } from './admin/categoria/categoria.component';
import { SubcategoriaComponent } from './admin/subcategoria/subcategoria.component';
import { UsuarioComponent } from './admin/usuario/usuario.component';
import { AdminComponent } from './admin/admin.component';
import { EstudiosClienteComponent } from './cliente/estudios-cliente/estudios-cliente.component';
import { SolicitudEstudioComponent } from './cliente/solicitud-estudio/solicitud-estudio.component';
import { ClienteComponent } from './cliente/cliente.component';
import { EstudiosEncuestadoComponent } from './encuestado/estudios-encuestado/estudios-encuestado.component';
import { AnalistaComponent } from './analista/analista.component';
import { EstudiosAnalistaComponent } from './analista/estudios-analista/estudios-analista.component';
import { CrearEstudioComponent } from './analista/crear-estudio/crear-estudio.component';
import { TipoComponent } from './admin/tipo/tipo.component';
import { PresentacionComponent } from './admin/presentacion/presentacion.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'dashboard/marcas', component: MarcaComponent},
  { path: 'admin/categorias', component: CategoriaComponent},
  { path: 'admin/marcas', component: MarcaComponent},
  { path: 'admin/tipos', component: TipoComponent},
  { path: 'admin/presentaciones', component: PresentacionComponent},
  { path: 'admin/usuarios', component: UsuarioComponent},
  { path: 'dashboard/subcategorias', component: SubcategoriaComponent},
  { path: 'dashboard/usuarios', component: UsuarioComponent},
  { path: 'admin', component: AdminComponent },
  { path: 'cliente', component: ClienteComponent },
  { path: 'cliente/estudios', component: EstudiosClienteComponent },
  { path: 'cliente/solicitar_estudio', component: SolicitudEstudioComponent },
  { path: 'encuestado/estudios', component: EstudiosEncuestadoComponent },
  { path: 'analista', component: AnalistaComponent },
  { path: 'analista/solicitudes', component: EstudiosAnalistaComponent },
  { path: 'analista/crearEncuesta', component: CrearEstudioComponent },


  { path: '', redirectTo: 'categorias', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
