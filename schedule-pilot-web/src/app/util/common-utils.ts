import { environment } from '../../environments/environment';

export class CommonUtils {
  static getRandomColorHex(): string {
    const hex = environment.components.dashboard['random.letters'];
    let color = '#';
    for (let i = 1; i <= 6; i++) {
      color += hex[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  static countdown(s: number): string {
    const d = Math.floor(s / (3600 * 24));
    s -= d * 3600 * 24;
    const h = Math.floor(s / 3600);
    s -= h * 3600;
    const m = Math.floor(s / 60);
    s -= m * 60;
    const tmp = [];
    (d) && tmp.push(d + ' días ');
    (d || h) && tmp.push(h + ' horas ');
    (d || h || m) && tmp.push(m + ' minutos ');
    tmp.push(s + ' segundos ');
    return tmp.join(' ');
  }
}
