### Using Java AES CBC mode to encrypt or decrypt data



### Put two byte[] into one using this method:

	public static byte[] add(byte[] a, byte[] b){
		byte[] data = new byte[a.length + b.length];
		System.arraycopy(a, 0, data, 0, a.length); 
		System.arraycopy(b, 0, data, a.length, b.length); 
		return data;
	}
	
	
### Change HexString to byte[] :
	
	public static byte[] hex_to_bytes(String hexString){
		char[] hex = hexString.toCharArray();
		int length = hex.length / 2;
		byte[] rawData = new byte[length];
		for (int i = 0; i < length; i++){
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127){
				value -= 256;
			}			
			rawData [i] = (byte) value;
		}
		 return rawData ;
	}
	
	
### Change byte[] to HexString :

	public static String byte_to_Hex(byte[] data){
		hexString = "";
		for(int i=0; i<b.length ; i++){
			hexString += Integer.toString((b[i] & 0xff) + 0x100,16).substring(1);
		}
		return hexString;
	}
