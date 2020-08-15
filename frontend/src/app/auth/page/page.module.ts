import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { PageService } from './page.service';
import { PageComponent } from './page.component';
import { PageEditComponent } from './edit/page-edit.component';

@NgModule({
  imports: [SharedModule],
  providers: [PageService],
  declarations: [PageComponent, PageEditComponent],
  entryComponents: [PageEditComponent],
})
export class PageModule {}
