import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { DeptComponent } from './dept/dept.component';

const routes: Routes = [{ path: 'dept', component: DeptComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class AuthRoutingModule {}
