import { Injectable } from '@angular/core';
import { PreguntaEstudio, PreguntaEstudio2 } from '../modelos/pregunta_estudio';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, tap} from 'rxjs/operators';
import { CategoriasService } from './categorias.service';
import { respuestaPregunta, respuestaPregunta2, respuestaPregunta3 } from '../modelos/respuestaPregunta';
import { Estudio } from '../modelos/estudio';
import { Pregunta, Pregunta3, PreguntaEncuesta } from '../modelos/pregunta';
import { Respuesta2 } from '../modelos/respuesta';

@Injectable({
  providedIn: 'root'
})
export class EncuestasService {

  preguntaEstudio: PreguntaEstudio[];
  estudio: Estudio[];
  preguntas: Pregunta[];
  preguntas2: Pregunta3[];

  constructor(public http: HttpClient) {
  }
  public url = '//localhost:8080/servicio-1.0-SNAPSHOT/api/';

  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  getPreguntasEncuesta(id: number, idUsuario: number): Observable<any>{
    console.log("entre1");
    console.log(id, idUsuario);
    return this.http.get<any>(this.url + 'encuesta/preguntas/'+id + '/'+idUsuario);
  }

  getRespuestasAsociadas(id:number){
    console.log("entre2");
    return this.http.get<respuestaPregunta3[]>(this.url + 'encuesta/respuestas/' +  id);
  }

  addRespuesta(respuesta: Respuesta2){
    console.log('entre');
    console.log (respuesta);
    return this.http.post(this.url +'respuesta/addRespuesta' , respuesta)
    .subscribe(
      response => {
        console.log('guardar respuestas' + response);
      },
      error => console.log('Error al guardar respuestas' + error)
    );
  }

  cambiarEstatus(idEstudio, idUsuario):Observable<any>{
    console.log('Estudio:', idEstudio);
    console.log('Encueatado:', idUsuario);
    return this.http.put<any>(this.url +'estudio/cambiarEstatusEncuestado/', idEstudio, +'/'+ idUsuario)
    .pipe(
      tap((dataObject: any) => console.log(`added estudio w/ id=${dataObject}`)),
      catchError(this.handleError)
    );
  }


    /// Error HandleError
    handleError(error): Observable<never> {
      let errorMessage = '';
      if (error.error instanceof ErrorEvent) {
        errorMessage = error.error.message;
      } else {
        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
      }
      window.alert(errorMessage);
      return throwError(errorMessage);
   }
}
