class Problem {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            String parameter = args[i];
            String value = args[i + 1];
            System.out.println(parameter + "=" + value);
        }
    }
}
