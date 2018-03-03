class Solution {
    public List<String> readBinaryWatch401(int num) {
        List<String> res = new LinkedList<>();
        recursive(0, 0, num, res);
        return res;
    }

    private void recursive(int time, int shifted, int rest, List<String> list) {
        if (shifted == 10) {
            int hour = time >> 6;
            int minute = time & 0x3f;

            if (hour < 12 && minute < 60)
                list.add(String.valueOf(hour) + (minute >= 10 ? ":" : ":0") + String.valueOf(minute));
            return;
        }

        time = time << 1;
        if (10 - shifted > rest)
            recursive(time, shifted + 1, rest, list);
        if (rest > 0)
            recursive(time | 1, shifted + 1, rest - 1, list);
    }
}
