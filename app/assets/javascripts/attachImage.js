function attachImage(fileInput, destinationId, previewId) {
    console.log(fileInput.files);
    const fileReader = new FileReader();
    fileReader.onload = () => {
        const dataUrl = fileReader.result;
        const destination = document.getElementById(destinationId);
        destination.value = dataUrl;
        const preview = document.getElementById(previewId);
        preview.src = dataUrl;
    };
    fileReader.readAsDataURL(fileInput.files[0])
}
