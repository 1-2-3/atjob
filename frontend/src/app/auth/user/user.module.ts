import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { UserService } from './user.service';
import { UserComponent } from './user.component';
import { UserEditComponent } from './edit/user-edit.component';
import { DeptService } from '../dept/dept.service';
import { RoleService } from '../role/role.service';

@NgModule({
  imports: [SharedModule],
  providers: [UserService, DeptService, RoleService],
  declarations: [UserComponent, UserEditComponent],
  entryComponents: [UserEditComponent],
})
export class UserModule {}
