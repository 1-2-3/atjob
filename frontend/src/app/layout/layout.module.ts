import { NgModule } from '@angular/core';
import { NzLayoutModule, NzMenuModule } from 'ng-zorro-antd';
import { SharedModule } from '../shared/shared.module';
import { AntDefaultComponent } from './ant-default/ant-default.component';
import { FullscreenComponent } from './fullscreen/fullscreen.component';
import { UserMenuService } from './user-menu.service';

const COMPONENTS = [AntDefaultComponent, FullscreenComponent];

@NgModule({
  imports: [SharedModule, NzLayoutModule, NzMenuModule],
  providers: [UserMenuService],
  declarations: [...COMPONENTS],
  exports: [...COMPONENTS],
})
export class LayoutModule {}
