package tech.meliora.natujenge.charset.util;

import tech.meliora.natujenge.charset.gsm0338.Gsm7BitCharsetProvider;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;

public class StringToByteConverter {

    public static byte[] convert(String message, String encodingCharset) throws UnsupportedEncodingException {

        if(encodingCharset.equalsIgnoreCase("X-Gsm7Bit")){

            //our own custom charset not supported in java...

            CharsetProvider provider = new Gsm7BitCharsetProvider();
            Charset charset = provider.charsetForName("X-Gsm7Bit");

            return message.getBytes(charset);

        } else {
            //use already built charset in java e.g. UTF-8 or UTF-16BE (UCS-2 equivalent - unicode that uses 2 bytes)
            return message.getBytes(encodingCharset);
        }

    }
}
