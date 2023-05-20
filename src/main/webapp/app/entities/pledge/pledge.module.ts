import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PledgeComponent } from './list/pledge.component';
import { PledgeDetailComponent } from './detail/pledge-detail.component';
import { PledgeUpdateComponent } from './update/pledge-update.component';
import { PledgeDeleteDialogComponent } from './delete/pledge-delete-dialog.component';
import { PledgeRoutingModule } from './route/pledge-routing.module';

@NgModule({
  imports: [SharedModule, PledgeRoutingModule],
  declarations: [PledgeComponent, PledgeDetailComponent, PledgeUpdateComponent, PledgeDeleteDialogComponent],
})
export class PledgeModule {}
