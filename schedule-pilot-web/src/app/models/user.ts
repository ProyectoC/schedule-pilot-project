import { CollageCareer } from './collage-career';
import { PasswordUser } from './password-user';
import { RolUser } from './rol-user';

export class User {
    public firstName: string;
    public lastName: string;
    public identification: string;
    public identificationCode: string;
    public email: string;
    public emailBackup: string;
    public password: string;
    public phoneNumber: string;
    public phoneCountryCode: string;
    public rolAccount: RolUser;
    public collegeCareer: CollageCareer;

    constructor() {
        this.collegeCareer = new CollageCareer();
        this.rolAccount = new RolUser();
    }
}
