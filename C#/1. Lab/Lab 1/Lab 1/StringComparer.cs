namespace Lab_1
{
    [Serializable]
    public class StringComparer : IComparer<string>
    {
        public int Compare(string a, string b)
        {
            if (a.Length < b.Length)
                return 1;
            if (a.Length > b.Length)
                return -1;
            return a.CompareTo(b);
        }
    }
}