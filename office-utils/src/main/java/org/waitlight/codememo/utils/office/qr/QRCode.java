package org.waitlight.codememo.utils.office.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCode {

    public static final int IMG_W = 720;
    public static final int IMG_H = 920;

    public static final int QR_CODE_W = 720;
    public static final int QR_CODE_H = 720;

    public static final int LOGO_X = QR_CODE_W / 5 * 2;
    public static final int LOGO_Y = QR_CODE_H / 5 * 2;
    public static final int LOGO_W = QR_CODE_W / 5;
    public static final int LOGO_H = QR_CODE_H / 5;

    public static final int LOGO_BORDER_LINE_WIDTH = 1;

    public static final int SUMMARY_BORDER_X = 10;
    public static final int SUMMARY_BORDER_Y = QR_CODE_H + 10;
    public static final int SUMMARY_BORDER_W = 700;
    public static final int SUMMARY_BORDER_H = 180;

    public static final int SUMMARY_BORDER_LINE_WIDTH = 4;

    public static final int SUMMARY_FONT_WEIGHT = 64;
    public static final int ORG_FONT_WEIGHT = 18;


    private final String content;
    private final String logo;
    private final String summary;
    private final String org;

    private BufferedImage image;

    private QRCode() {
        this.content = null;
        this.logo = null;
        this.summary = null;
        this.org = null;
    }

    public QRCode(String content, String logo) throws IOException, WriterException {
        this.content = content;
        this.logo = logo;
        this.summary = null;
        this.org = null;
        drawImage(content, logo, null, null);
    }

    public QRCode(String content, String logo, String summary, String org) throws IOException, WriterException {
        this.content = content;
        this.logo = logo;
        this.summary = summary;
        this.org = org;
        drawImage(content, logo, summary, org);
    }

    public void drawImage(String content, String logo, String summary, String org) throws WriterException, IOException {
        if (StringUtils.isAnyBlank(summary, org)) {
            image = new BufferedImage(QR_CODE_W, QR_CODE_H, BufferedImage.TYPE_INT_RGB);
        } else {
            image = new BufferedImage(IMG_W, IMG_H, BufferedImage.TYPE_INT_RGB);
        }

        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, IMG_W, IMG_H);

        drawQrCode(g2d, content, logo);
        drawSummary(g2d, summary, org);

        g2d.dispose();
        image.flush();
    }

    public void toPng(String outputPath) throws IOException {
        if (StringUtils.isBlank(summary)) {
            ImageIO.write(image, "png", new File(outputPath + "/" + System.currentTimeMillis() + ".png"));
        } else {
            ImageIO.write(image, "png", new File(outputPath + "/" + summary + System.currentTimeMillis() + ".png"));
        }
    }

    public OutputStream toOutputStream() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return os;
    }

    private void drawQrCode(Graphics2D g2d, String qrCode, String logo) throws WriterException, IOException {
        drawQrCode(g2d, qrCode);
        drawLogo(g2d, logo);
    }

    private void drawQrCode(Graphics2D g2d, String qrCode) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 0);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCode, BarcodeFormat.QR_CODE, QR_CODE_W, QR_CODE_H, hints);
        BufferedImage qrBufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        g2d.drawImage(qrBufferedImage, 0, 0, QR_CODE_W, QR_CODE_H, null);
    }

    private void drawLogo(Graphics2D g2d, String logo) throws IOException {
        if (StringUtils.isBlank(logo)) {
            return;
        }

        BufferedImage logoImage = ImageIO.read(new File(logo));
        g2d.drawImage(logoImage, LOGO_X, LOGO_Y, LOGO_W, LOGO_H, null);
        drawBorder(g2d, LOGO_BORDER_LINE_WIDTH, LOGO_X + 2d, LOGO_Y + 2d, LOGO_W - 4d, LOGO_H - 4d, 20d, 20d);
    }

    private void drawSummary(Graphics2D g2d, String summary, String org) {
        drawBorder(g2d, SUMMARY_BORDER_LINE_WIDTH, SUMMARY_BORDER_X, SUMMARY_BORDER_Y, SUMMARY_BORDER_W, SUMMARY_BORDER_H, 50d, 50d);
        drawSummary(g2d, summary);
        drawOrg(g2d, org);
    }

    private void drawBorder(Graphics2D g2d, float lineWidth,
                            double x, double y,
                            double width, double height,
                            double arcw, double arch) {
        g2d.setStroke(new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(new Color(128, 128, 128));
        g2d.draw(new RoundRectangle2D.Double(x, y, width, height, arcw, arch));
    }

    private void drawSummary(Graphics2D g2d, String summary) {
        if (StringUtils.isBlank(summary)) {
            return;
        }

        Font font = new Font(Font.SERIF, Font.BOLD, SUMMARY_FONT_WEIGHT);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(summary);
        int textHeight = fontMetrics.getHeight();

        int x = (IMG_W - textWidth) / 2;
        int y = IMG_H - textHeight;
        g2d.drawString(summary, x, y);
    }

    private void drawOrg(Graphics2D g2d, String org) {
        if (StringUtils.isBlank(org)) {
            return;
        }

        Font font = new Font(Font.SERIF, Font.PLAIN, ORG_FONT_WEIGHT);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int contentWidth = fontMetrics.stringWidth(org);
        int contentHeight = fontMetrics.getHeight();

        int x = IMG_W - 20 - SUMMARY_BORDER_LINE_WIDTH - contentWidth;
        int y = IMG_H - 8 - SUMMARY_BORDER_LINE_WIDTH - (contentHeight / 2);
        g2d.drawString(org, x, y);
    }

}