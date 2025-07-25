import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { URLUtilities } from 'src/app/core/static/variables/url/URLUtilities';
import { MainPageComponent } from './main-page/main-page.component';

const routes: Routes = [
  {
    path: URLUtilities.getLogin(),
    component: MainPageComponent
  }, {
    path: URLUtilities.getAccessDenied(),
    component: AccessDeniedComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class AccessAplicationRoutingModule {

}
