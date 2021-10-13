package kr.pe.fourj.exception;

public class Exception {
	
	public static class NotFoundException extends Throwable {
		public NotFoundException(String s) {
			
		}
	}
	
    public static class ArgumentNullException extends Throwable {
        public ArgumentNullException(String s) {
        }
    }

    public static class InvalidArgumentException extends Throwable {
        public InvalidArgumentException(String s) {
        }
    }

}
