import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { DeptComponent } from './dept/dept.component';
import { RoleComponent } from './role/role.component';
import { PageComponent } from './page/page.component';

const routes: Routes = [
  { path: 'dept', component: DeptComponent },
  { path: 'role', component: RoleComponent },
  { path: 'page', component: PageComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class AuthRoutingModule {}
