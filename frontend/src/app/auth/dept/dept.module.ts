import { DeptEditComponent } from './edit/dept-edit.component';
import { DeptComponent } from './dept.component';
import { DeptService } from './dept.service';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [SharedModule],
  providers: [DeptService],
  declarations: [DeptComponent, DeptEditComponent],
  entryComponents: [DeptEditComponent],
})
export class DeptModule {}
