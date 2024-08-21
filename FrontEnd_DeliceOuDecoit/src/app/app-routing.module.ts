import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { UserComponent } from './user/user.component';
import { AuthGuard } from 'src/_auth/auth.guard';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {path:'admin',component:AdminComponent,canActivate:[AuthGuard],data:{roles:['ADMIN']} },
  {path:'user',component:UserComponent,canActivate:[AuthGuard],data:{roles:['USER']}},
  {path:'forbidden',component:ForbiddenComponent},

  // Add other routes here
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
