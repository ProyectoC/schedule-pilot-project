import { Component, OnInit, ViewChild } from '@angular/core';
import { RequestCheckIn } from '@models/request-check-in/request-check-in';
import { LoanProcessComponent } from '../loan-process/loan-process.component';
import { LoansService } from '@services/loans/loans.service';
import { MessagesService } from '@services/messages/message.service';
import { Router } from '@angular/router';
import { RoutingConstants } from '@constants/routing-constants';

@Component({
  selector: 'app-loans-container',
  templateUrl: './loans-container.component.html',
  styleUrls: ['./loans-container.component.scss']
})
export class LoansContainerComponent implements OnInit {


  @ViewChild(LoanProcessComponent, { static: true })
  loanProcessComponent: LoanProcessComponent;

  constructor(public loanService: LoansService, public messageService: MessagesService,
    private router: Router) { }

  ngOnInit(): void {
    this.loanProcessComponent.productRequests = [];
  }

  sendRequestCheckIn($event: RequestCheckIn) {
    this.messageService.generateConfirmMessage(null, 'Se creará una nueva solicitud.').then(
      (responseUser: boolean) => {
        if (responseUser) {
          this.loanProcessComponent.isLoading = true;
          this.loanService.createRequestCheckIn($event).subscribe((bodyResponse: any) => {
            this.messageService.generateSuccessMessage('Crear Solicitud', 'La solicitud se ha creado correctamente.');
            this.loanProcessComponent.isLoading = false;
            this.router.navigate([RoutingConstants.URL_HOME]).then(() => { });
          });
        }
      });
  }

}
