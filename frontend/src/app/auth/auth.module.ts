import { NgModule } from '@angular/core';
import { AuthRoutingModule } from './auth-routing.module';
import { DeptModule } from './dept/dept.module';
import { RoleModule } from './role/role.module';
import { PageModule } from './page/page.module';

@NgModule({
  imports: [AuthRoutingModule, DeptModule, RoleModule, PageModule],
  providers: [],
  declarations: [],
})
export class AuthModule {}
