import { NgModule } from '@angular/core';
import { AuthRoutingModule } from './auth-routing.module';
import { DeptModule } from './dept/dept.module';
import { RoleModule } from './role/role.module';
import { PageModule } from './page/page.module';
import { UserModule } from './user/user.module';
import { LoginModule } from './login/login.module';

@NgModule({
  imports: [AuthRoutingModule, DeptModule, RoleModule, PageModule, UserModule, LoginModule],
  providers: [],
  declarations: [],
})
export class AuthModule {}
