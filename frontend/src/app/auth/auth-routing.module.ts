import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { DeptComponent } from './dept/dept.component';
import { RoleComponent } from './role/role.component';
import { PageComponent } from './page/page.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: 'dept', component: DeptComponent },
  { path: 'role', component: RoleComponent },
  { path: 'page', component: PageComponent },
  { path: 'user', component: UserComponent },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class AuthRoutingModule {}
