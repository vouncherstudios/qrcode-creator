/*
 * MIT License
 *
 * Copyright (c) Vouncher Studios <contact@vouncherstudios.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vouncherstudios.qrcodegenerator;

import com.vouncherstudios.qrcodegenerator.app.QrCodeGeneratorApp;
import java.util.Set;
import javax.annotation.Nonnull;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "qrcode-generator",
    mixinStandardHelpOptions = true,
    version = QrCodeGeneratorProperties.VERSION,
    description = QrCodeGeneratorProperties.DESCRIPTION)
public final class QrCodeGeneratorBootstrap implements Runnable {
  private QrCodeGeneratorBootstrap() {}

  private static QrCodeGeneratorApp app;

  @Option(
      names = {"-p", "--port"},
      description = "The port to run the server on")
  private int port = 7000;

  @Option(
      names = {"-e", "--exempt"},
      description = "The IP address to exempt from the rate limit")
  private Set<String> exemptIps = Set.of("127.0.0.1");

  @Option(
      names = {"-u", "--unknown-redirect"},
      description = "The URL to redirect to when the route is unknown")
  private String unknownRedirect = "https://vouncherstudios.com";

  @Option(
      names = {"-d", "--data-limit"},
      description = "The maximum data limit for a QR code")
  private int dataLimit = 250;

  public static void main(String[] args) {
    new CommandLine(new QrCodeGeneratorBootstrap()).execute(args);
  }

  @Override
  public void run() {
    app = new QrCodeGeneratorApp(this.port, this.exemptIps, this.unknownRedirect, this.dataLimit);
  }

  @Nonnull
  public static QrCodeGeneratorApp getApp() {
    return app;
  }
}
