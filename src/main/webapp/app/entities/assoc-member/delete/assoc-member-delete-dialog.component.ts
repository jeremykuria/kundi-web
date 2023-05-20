import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssocMember } from '../assoc-member.model';
import { AssocMemberService } from '../service/assoc-member.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './assoc-member-delete-dialog.component.html',
})
export class AssocMemberDeleteDialogComponent {
  assocMember?: IAssocMember;

  constructor(protected assocMemberService: AssocMemberService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assocMemberService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
