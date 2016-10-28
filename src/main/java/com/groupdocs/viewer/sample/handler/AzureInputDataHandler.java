package com.groupdocs.viewer.sample.handler;

import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.cache.CachedDocumentDescription;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.handler.input.IInputDataHandler;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsAccountAndKey;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

/**
 * The type Azure input data handler.
 */
public class AzureInputDataHandler implements IInputDataHandler {
    /// <summary>
    /// The blob delimiter.
    /// </summary>
    private static final String DELIMITER = "/";
    /// <summary>
    /// The endpoint template.
    /// </summary>
    private static final String ENDPOINT_TEMPLATE = "https://{account-name}.blob.core.windows.net/";
    /// <summary>
    /// The cloud blob container.
    /// </summary>
    private CloudBlobContainer _container;

    /**
     * Instantiates a new Azure input data handler.
     * @param accountName the account name
     * @param accountKey the account key
     * @param containerName the container name
     * @throws Exception the exception
     */
/// <summary>
    /// Initializes a new instance of the <see cref="AzureInputDataHandler"/> class.
    /// </summary>
    /// <param name="accountName"></param>
    /// <param name="accountKey"></param>
    /// <param name="containerName"></param>
    public AzureInputDataHandler(String accountName, String accountKey, String containerName) throws Exception {
        this(getEndpoint(accountName), accountName, accountKey, containerName);
    }

    /**
     * Instantiates a new Azure input data handler.
     * @param endpoint the endpoint
     * @param accountName the account name
     * @param accountKey the account key
     * @param containerName the container name
     * @throws Exception the exception
     */
/// <summary>
    /// Initializes a new instance of the <see cref="AzureInputDataHandler"/> class.
    /// </summary>
    /// <param name="endpoint">The endpoint e.g. https://youraccountname.blob.core.windows.net/ </param>
    /// <param name="accountName">The account name.</param>
    /// <param name="accountKey">The account key.</param>
    /// <param name="containerName">The container name.</param>
    public AzureInputDataHandler(URI endpoint, String accountName, String accountKey, String containerName) throws Exception {
        try {
            StorageCredentials storageCredentials = new StorageCredentialsAccountAndKey(accountName, accountKey);
            CloudStorageAccount cloudStorageAccount = new CloudStorageAccount(storageCredentials, endpoint, null, null, null);
            CloudBlobClient cloudBlobClient = cloudStorageAccount.createCloudBlobClient();
//            TimeSpan serverTimeout = cloudBlobClient.getDefaultRequestOptions().getServerTimeout;
//            cloudBlobClient.getDefaultRequestOptions().setServerTimeout = TimeSpan.FromSeconds(10);
            _container = cloudBlobClient.getContainerReference(containerName);
            _container.createIfNotExists();
//            cloudBlobClient.getDefaultRequestOptions().setServerTimeout = serverTimeout;
        } catch (StorageException e) {
            throw new Exception("Unable to recognize that Account Name/Account Key or container name is invalid.", e);
        } catch (Exception e) {
            throw new Exception("Unable to recognize that Account Name/Account Key.", e);
        }
    }

    /// <summary>
    /// Gets the file description.
    /// </summary>
    /// <param name="guid">The unique identifier.</param>
    /// <returns>GroupDocs.Viewer.Domain.FileDescription.</returns>
    public FileDescription getFileDescription(String guid) throws Exception {
        try {
            String blobName = getNormalizedBlobName(guid);
            final CloudBlockBlob blob = _container.getBlockBlobReference(blobName);
            blob.downloadAttributes();
            ;
            return new FileDescription(blobName, false) {{
                setLastModificationDate(getDateTimeOrEmptyDate(blob.getProperties().getLastModified()));
                setSize(blob.getProperties().getLength());
            }};
        } catch (StorageException ex) {
            throw new Exception("Unabled to get file description.", ex);
        }
    }

    public InputStream getFile(String guid) throws Exception {
        try {
            String blobName = getNormalizedBlobName(guid);
            CloudBlob blob = _container.getBlockBlobReference(blobName);
            ByteArrayOutputStream memoryStream = new ByteArrayOutputStream();
            blob.download(memoryStream);
            return new ByteArrayInputStream(memoryStream.toByteArray());
        } catch (StorageException ex) {
            throw new Exception("Unabled to get file.", ex);
        }
    }

    /// <summary>
    /// Gets the last modification date.
    /// </summary>
    /// <param name="guid">The unique identifier.</param>
    /// <returns>System.DateTime.</returns>
    public Date getLastModificationDate(String guid) throws Exception {
        FileDescription fileDescription = getFileDescription(guid);
        return fileDescription.getLastModificationDate();
    }

    /// <summary>
    /// Loads files/folders structure for specified path
    /// </summary>
    /// <param name="fileTreeOptions">The file tree options.</param>
    /// <returns>System.Collections.Generic.List&lt;GroupDocs.Viewer.Domain.FileDescription&gt;.</returns>
    public List<FileDescription> loadFileTree(FileTreeOptions fileTreeOptions) throws Exception {
        try {
            String path = getNormalizedBlobName(fileTreeOptions.getPath());
            List<FileDescription> fileTree = getFileTree(path);
            switch (fileTreeOptions.getOrderBy()) {
                case Name:
                    fileTree = fileTreeOptions.getOrderAsc() ? orderByName(fileTree, false) : orderByName(fileTree, true);
                    break;
                case LastModificationDate:
                    fileTree = fileTreeOptions.getOrderAsc() ? orderByLastModificationDate(fileTree, false) : orderByLastModificationDate(fileTree, true);
                    break;
                case Size:
                    fileTree = fileTreeOptions.getOrderAsc() ? orderBySize(fileTree, false) : orderBySize(fileTree, true);
                    break;
                default:
                    break;
            }
            return fileTree;
        } catch (StorageException ex) {
            throw new Exception("Failed to load file tree.", ex);
        }
    }

    @Override
    public void saveDocument(CachedDocumentDescription cachedDocumentDescription, InputStream documentStream) throws Exception {
        // ...
    }

    private List<FileDescription> orderBySize(List<FileDescription> fileTree, final boolean desc) {
        Collections.sort(fileTree, new Comparator<FileDescription>() {
            @Override
            public int compare(FileDescription o1, FileDescription o2) {
                final long result = o1.getSize() - o2.getSize();
                return (int) (desc ? result * -1 : result);
            }
        });
        return fileTree;
    }

    private List<FileDescription> orderByLastModificationDate(List<FileDescription> fileTree, final boolean desc) {
        Collections.sort(fileTree, new Comparator<FileDescription>() {
            @Override
            public int compare(FileDescription o1, FileDescription o2) {
                final int result = o1.getLastModificationDate().compareTo(o2.getLastModificationDate());
                return desc ? result * -1 : result;
            }
        });
        return fileTree;
    }

    private List<FileDescription> orderByName(List<FileDescription> fileTree, final boolean desc) {
        Collections.sort(fileTree, new Comparator<FileDescription>() {
            @Override
            public int compare(FileDescription o1, FileDescription o2) {
                final int result = o1.getName().compareTo(o2.getName());
                return desc ? result * -1 : result;
            }
        });
        return fileTree;
    }

    /// <summary>
    /// Gets the endpoint e.g. https://youraccountname.blob.core.windows.net/
    /// </summary>
    /// <param name="accountName">The account name.</param>
    /// <returns>Endpoint Uri.</returns>
    private static URI getEndpoint(String accountName) throws Exception {
        String endpoint = ENDPOINT_TEMPLATE.replace("{account-name}", accountName);
        return new URI(endpoint);
    }

    /// <summary>
    /// Gets the file tree.
    /// </summary>
    /// <param name="blobName">The blob name.</param>
    /// <returns>The file tree.</returns>
    private List<FileDescription> getFileTree(String blobName) throws Exception {
        CloudBlobDirectory directory = _container.getDirectoryReference(blobName);
        List<FileDescription> fileTree = new ArrayList<FileDescription>();
        for (ListBlobItem blob : directory.listBlobs()) {
            FileDescription fileDescription;
            if (blob instanceof CloudBlobDirectory) {
                CloudBlobDirectory blobDirectory = (CloudBlobDirectory) blob;
                fileDescription = new FileDescription(blobDirectory.getPrefix(), true);
            } else {
                final CloudBlob blobFile = (CloudBlob) blob;
                fileDescription = new FileDescription(blobFile.getName(), false) {{
                    setSize(blobFile.getProperties().getLength());
                    setLastModificationDate(getDateTimeOrEmptyDate(blobFile.getProperties().getLastModified()));
                }};
            }
            fileTree.add(fileDescription);
        }
        return fileTree;
    }

    /// <summary>
    /// Gets normalized blob name, updates guid from dir\\file.ext to dir/file.ext
    /// </summary>
    /// <param name="guid">The unique identifier.</param>
    /// <returns>Normalized blob name.</returns>
    private String getNormalizedBlobName(String guid) throws Exception {
        String modifiedGuid = guid.replaceAll("\\+", DELIMITER);
        while (modifiedGuid.startsWith(DELIMITER)) {
            modifiedGuid = modifiedGuid.substring(1);
        }
        while (modifiedGuid.endsWith(DELIMITER)) {
            modifiedGuid = modifiedGuid.substring(0, modifiedGuid.length() - 1);
        }
        return modifiedGuid;
    }

    /// <summary>
    /// Gets date time or empty date.
    /// </summary>
    /// <param name="dateTimeOffset">The date time offset.</param>
    /// <returns>Date time or empty date.</returns>
    private Date getDateTimeOrEmptyDate(Date dateTimeOffset) throws Exception {
        return dateTimeOffset != null ? dateTimeOffset : new Date(1, 1, 1);
    }
}
