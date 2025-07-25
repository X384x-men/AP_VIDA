import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from './loader.component';
import { MaterialModule } from '../material/material-module';
import { LoaderService } from 'src/app/core/services/loader/loader.service';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule
  ],
  declarations: [LoaderComponent],
  exports: [LoaderComponent],
  providers: [
    LoaderService
  ]
})
export class LoaderModule { }
