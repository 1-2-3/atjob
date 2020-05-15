import { NgModule } from '@angular/core';
import { AuthRoutingModule } from './auth-routing.module';
import { DeptModule } from './dept/dept.module';

@NgModule({
  imports: [AuthRoutingModule, DeptModule],
  providers: [],
  declarations: [],
})
export class AuthModule {}
