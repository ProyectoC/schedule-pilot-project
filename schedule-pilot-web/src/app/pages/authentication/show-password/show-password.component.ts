import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-show-password',
  templateUrl: './show-password.component.html',
  styleUrls: ['./show-password.component.scss']
})
export class ShowPasswordComponent implements OnInit {

  @Input() elementId: string;

  constructor() { }

  ngOnInit(): void {
  }

  showPassword() {
    var x: any = document.getElementById(this.elementId);
    if (x.type === 'password') {
      x.type = 'text';
      document.getElementById('icon-password').classList.add('fa-eye-slash');
      document.getElementById('icon-password').classList.remove('fa-eye');
    } else {
      x.type = 'password';
      document.getElementById('icon-password').classList.remove('fa-eye-slash');
      document.getElementById('icon-password').classList.add('fa-eye');
    }
  }

}
