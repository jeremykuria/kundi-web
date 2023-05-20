import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AssocMemberComponent } from './list/assoc-member.component';
import { AssocMemberDetailComponent } from './detail/assoc-member-detail.component';
import { AssocMemberUpdateComponent } from './update/assoc-member-update.component';
import { AssocMemberDeleteDialogComponent } from './delete/assoc-member-delete-dialog.component';
import { AssocMemberRoutingModule } from './route/assoc-member-routing.module';

@NgModule({
  imports: [SharedModule, AssocMemberRoutingModule],
  declarations: [AssocMemberComponent, AssocMemberDetailComponent, AssocMemberUpdateComponent, AssocMemberDeleteDialogComponent],
})
export class AssocMemberModule {}
