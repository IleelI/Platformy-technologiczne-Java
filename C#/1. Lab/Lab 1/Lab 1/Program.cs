using System.Runtime.Serialization.Formatters.Binary;

namespace Lab_1
{
    internal static class Program
    {
        public static void Main(string[] args)
        {
            var directoryPath = args[0];
            Console.WriteLine(directoryPath);
            // Task 1 & 2
            ListContentsOfDirectory(directoryPath);
            // Task 3 
             DirectoryInfo directoryInfo = new DirectoryInfo(args[0]);
             DateTime youngestElement = directoryInfo.GetYoungestElementFromDirectory();
             Console.WriteLine($"{youngestElement.Day}.{youngestElement.Month}.{youngestElement.Year}");
             FileSystemInfo fileSystemInfo = new DirectoryInfo(args[0]);
             Console.WriteLine(fileSystemInfo.GetRashFromDirectory());
             foreach (KeyValuePair<string, long> entry in GetDirectoryElements(directoryPath))
             {
                 Console.WriteLine($"Name: {entry.Key}, Size: {entry.Value}");
             }
             SaveSortedDictionary(GetDirectoryElements(directoryPath), "test.dat");
        }
        public static void ListContentsOfDirectory(string path, int indentationLevel = 0)
        {
            string indentations = "";
            for (var indentation = 0; indentation < indentationLevel; indentation += 1)
            {
                indentations += "\t";
            }
            foreach (string directoryPath in Directory.GetDirectories(path))
            {
                DirectoryInfo fsi = new DirectoryInfo(directoryPath);
                int elementsCount = Directory.GetFileSystemEntries(directoryPath).Length;
                Console.WriteLine($"{indentations}Name: {fsi.Name}, Size: {elementsCount}, RASH: {fsi.GetRashFromDirectory()}");
                ListContentsOfDirectory(directoryPath, indentationLevel + 1);
            }
            foreach (string filePath in Directory.GetFiles(path))
            {
                FileInfo fsi = new FileInfo(filePath);
                Console.WriteLine($"{indentations}Name: {fsi.Name}, Size: {fsi}, RASH: {fsi.GetRashFromDirectory()}");
            }
        }
        public static SortedDictionary<string, long> GetDirectoryElements(string directoryPath)
        {
            SortedDictionary<string, long> directoryElements = new SortedDictionary<string, long>(new StringComparer());
            string[] subdirectories = Directory.GetDirectories(directoryPath);
            string[] files = Directory.GetFiles(directoryPath);
            foreach (string subdirectoryPath in subdirectories)
            {
                DirectoryInfo di = new DirectoryInfo(subdirectoryPath);
                int subdirectorySize = Directory.GetFileSystemEntries(subdirectoryPath).Length;
                directoryElements.Add(di.Name, subdirectorySize);
            }
            foreach (string filePath in files)
            {
                FileInfo fi = new FileInfo(filePath);
                directoryElements.Add(fi.Name, fi.Length);
            }
            return directoryElements;
        }

        public static void SaveSortedDictionary(SortedDictionary<string, long> sortedDictionary, string fileName)
        {
            FileStream fs = new FileStream(fileName, FileMode.Create);
            BinaryFormatter bf = new BinaryFormatter();
            try
            {
                bf.Serialize(fs, sortedDictionary);
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.Message);
            }
            fs.Close();
            Deserialize(fileName);
        } 

        public static void Deserialize(string fileName)
        {
            SortedDictionary<string, long> sortedDictionary = new SortedDictionary<string, long>(new StringComparer());
            FileStream fs = new FileStream(fileName, FileMode.Open);
            try
            {
                BinaryFormatter bf = new BinaryFormatter();
                sortedDictionary = (SortedDictionary<string, long>)bf.Deserialize(fs);
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception.Message);
            }
            foreach(var file in sortedDictionary)
            {
                Console.WriteLine($"{file.Key} -> {file.Value}");
            }
        }
    }

    public static class Extensions
    {
        // Task 3a
        public static DateTime GetYoungestElementFromDirectory(this DirectoryInfo directoryInfo)
        {
            DateTime youngest = new DateTime(1453, 1, 1);
            foreach (DirectoryInfo df in directoryInfo.GetDirectories())
            {
                DateTime directoryYoungest = df.GetYoungestElementFromDirectory();
                youngest = youngest < directoryYoungest ? directoryYoungest : youngest;
            }
            foreach (FileInfo fi in directoryInfo.GetFiles())
            {
                DateTime fileYoungest = fi.CreationTime;
                youngest = youngest < fileYoungest ? fileYoungest : youngest;
            }
            return youngest;
        }

        // Task 3b
        public static string GetRashFromDirectory(this FileSystemInfo fsi)
        {
            string rashAttributes = "";
            FileAttributes attributes = fsi.Attributes;
            rashAttributes += (attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly ? 'R' : '-';
            rashAttributes += (attributes & FileAttributes.Archive) == FileAttributes.Archive ? 'A' : '-';
            rashAttributes += (attributes & FileAttributes.System) == FileAttributes.System ? 'S' : '-';
            rashAttributes += (attributes & FileAttributes.Hidden) == FileAttributes.Hidden ? 'H' : '-';
            return rashAttributes;
        }
    }

}